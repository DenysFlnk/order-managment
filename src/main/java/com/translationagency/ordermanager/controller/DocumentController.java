package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.service.DocumentService;
import com.translationagency.ordermanager.to.DocumentTo;
import com.translationagency.ordermanager.util.DocumentUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Document create(@PathVariable int id, @RequestBody Document document) {
        log.info("create document {} for order {}", document, id);
        return documentService.create(document, id);
    }

    @PutMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @PathVariable int documentId,
                       @RequestBody Document document) {
        log.info("update document {} with data {} for order {}", documentId, document, id);
        documentService.update(document, id);
    }

    @DeleteMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int documentId) {
        log.info("delete document {} in order {}", documentId, id);
        documentService.delete(id, documentId);
    }

    @PatchMapping("/{documentId}/complexity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeComplexity(@PathVariable int id, @PathVariable int documentId,
                                 @RequestParam boolean isHardComplexity) {
        log.info("changeComplexity of {}, isHardComplexity to {}", documentId, isHardComplexity);
        documentService.changeComplexity(id, documentId, isHardComplexity);
    }

    @PatchMapping("/{documentId}/translator")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeTranslator(@PathVariable int id, @PathVariable int documentId,
                                 @RequestParam int translatorId) {
        log.info("changeTranslator for document {} to translator {}", documentId, translatorId);
        documentService.changeTranslator(id, documentId, translatorId);
    }
}
