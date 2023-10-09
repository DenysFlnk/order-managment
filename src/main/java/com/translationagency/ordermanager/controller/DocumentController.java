package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.service.DocumentService;
import com.translationagency.ordermanager.to.document.DocumentTo;
import com.translationagency.ordermanager.util.DocumentUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = DocumentController.DOCUMENT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class DocumentController {

    public static final String DOCUMENT_REST_URL = "rest-api/orders/{id}/documents";

    private DocumentService documentService;

    @GetMapping
    public List<DocumentTo> getAll(@PathVariable int id) {
        log.info("get all for order {}", id);
        List<Document> documents = documentService.getAllByOrder(id);
        return DocumentUtil.getTos(documents);
    }

    @GetMapping("/{documentId}")
    public DocumentTo get(@PathVariable int id, @PathVariable int documentId) {
        log.info("get document {} in order {}", documentId, id);
        Document document = documentService.get(id, documentId);
        return DocumentUtil.getTo(document);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Document create(@PathVariable int id, @RequestParam(required = false) Integer translatorId,
                           @Valid @RequestBody Document document) {
        log.info("create document {} for order {}, translatorId {}", document, id, translatorId);
        return translatorId == null ?
                documentService.create(document, id) : documentService.createWithTranslator(document, id, translatorId);
    }

    @PutMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id,  @RequestParam(required = false) Integer translatorId,
                       @PathVariable int documentId,
                       @Valid @RequestBody Document document) {
        log.info("update document {} with data {} for order {}, translatorId {}", documentId, document, id, translatorId);
        assureIdConsistent(document, documentId);
        if (translatorId == null) {
            documentService.update(document, id);
        } else {
            documentService.updateWithTranslator(document, id, translatorId);
        }
    }

    @DeleteMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int documentId) {
        log.info("delete document {} in order {}", documentId, id);
        documentService.delete(id, documentId);
    }

    @PatchMapping("/{documentId}/complexity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DocumentTo changeComplexity(@PathVariable int id, @PathVariable int documentId,
                                 @RequestParam boolean isHardComplexity, @RequestParam boolean updateRate) {
        log.info("changeComplexity of {}, isHardComplexity to {}", documentId, isHardComplexity);
        return DocumentUtil.getTo(documentService.changeComplexity(id, documentId, isHardComplexity, updateRate));
    }

    @PatchMapping("/{documentId}/translator")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeTranslator(@PathVariable int id, @PathVariable int documentId,
                                 @RequestParam int translatorId) {
        log.info("changeTranslator for document {} to translator {}", documentId, translatorId);
        documentService.changeTranslator(id, documentId, translatorId);
    }
}
