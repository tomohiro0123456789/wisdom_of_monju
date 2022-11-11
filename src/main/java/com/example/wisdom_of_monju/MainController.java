package com.example.wisdom_of_monju;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.wisdom_of_monju.entity.Board;
import com.example.wisdom_of_monju.entity.TopicCsv;
import com.example.wisdom_of_monju.form.BoardForm;
import com.example.wisdom_of_monju.service.BoardService;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

//進捗状況　新規投稿と投稿一覧は実装済み。
//細かなバリデーションとファイルのアップデートと保存については後回しにしている
//あとはレイアウトがミスっているので、一通り機能が実装されたら取り組むのと
//コードがあまりに汚いので随時、整えていく。

@Controller
@RequestMapping("/")
public class MainController {

	private final BoardService boardService;

	@Autowired
	public MainController(BoardService boardService) {
		this.boardService = boardService;

	}

	@GetMapping("/")

	public String index(Model model, BoardForm boardForm) {
		return "main/index";

	}

//レイアウト修正とデータ取得順番（ソート機能を実装）
	// ファイルのアップロード機能追加
	@GetMapping("/topiclist")
	public String topiclist(Model model) {

		List<Board> list = boardService.findAll();
		model.addAttribute("boardList", list);
		return "topic/topiclist";
	}

	@GetMapping("/newtopic")
	public String newtopic(@ModelAttribute("complete") String complete) {
		return "topic/newtopic";
	}

//管理者ページのログイン機能を制作予定
	@GetMapping("/admin")
	public String admin(Model model) {
		List<Board> list = boardService.findAll();
		model.addAttribute("boardList", list);
		return "admin/administrator";
	}

	@PostMapping("/form")
	public String formGoBack(BoardForm boardForm) {
		return "topic/newtopic";
	}

//レイアウト修正必須
	@PostMapping("/confirm")
	public String confirm(@Validated BoardForm boardForm, BindingResult result) {
		if (result.hasErrors()) {

			return "topic/newtopic";
		}

		return "topic/confirm";

	}

//こちらもレイアウト修正
	@PostMapping("/complete")
	public String complete(@Validated BoardForm boardForm, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "topic/confirm";
		}
		Board board = new Board();
		board.setTitle(boardForm.getTitle());
		board.setName(boardForm.getName());
		board.setText(boardForm.getText());
//		board.setImage(boardForm.image());
		board.setCreated(LocalDateTime.now());

		boardService.save(board);

		redirectAttributes.addFlashAttribute("complete", "投稿完了！");
		return "redirect:/newtopic";

	}

	@RequestMapping(value = "/admin/topic.csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
			+ "; charset=UTF-8; Content-Disposition: attachment")
	@ResponseBody
	public Object downloadCsv(ModelMapper modelMapper) throws IOException {

		List<Board> list = boardService.findAll();
		Type listType = new TypeToken<List<TopicCsv>>() {
		}.getType();
		List<TopicCsv> csv = modelMapper.map(list, listType);
//LocalDateTimeはcsvの出力が行えないみたいなので、使用できるようにフォーマットを作成した
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		CsvMapper csvMapper = new CsvMapper();
		javaTimeModule.addDeserializer(LocalDateTime.class,
				new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy/M/d")));
		csvMapper.registerModule(javaTimeModule);

		CsvSchema schema = csvMapper.schemaFor(TopicCsv.class).withHeader();

		return csvMapper.writer(schema).writeValueAsString(csv);
	}

}
