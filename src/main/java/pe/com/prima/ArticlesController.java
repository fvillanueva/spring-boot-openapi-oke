package pe.com.prima;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles/v1")
public class ArticlesController {

    private ArticlesService service;

    public ArticlesController(ArticlesService service) {
        this.service = service;
    }

    @GetMapping(value = "/getmultiple", params = "amount", produces = "application/json")
    public ResponseEntity<Iterable<Article>> getMultipleArticles(
            @RequestParam(name = "amount", required = true) int amount) {
        return ResponseEntity.ok(service.getMultipleArticles(amount));
    }


    @GetMapping(value = "/getone", params = "id", produces = "application/json")
    public ResponseEntity<Article> getSingleArticle(@RequestParam(name = "id", required = true) String id) {
        return ResponseEntity.ok(service.getArticle(id));
    }


    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Article> createNewArticle(@RequestBody Article article) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createArticle(article));
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<String> handleClientExceptions(ClientException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ClientException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}