package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.service.ApostilleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ApostilleController.APOSTILLE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class ApostilleController {

    public static final String APOSTILLE_REST_URL = "rest-api/orders/{id}/apostilles";

    private ApostilleService apostilleService;

    @GetMapping
    public List<Apostille> getAll(@PathVariable int id) {
        log.info("getAll for order {}", id);
        return apostilleService.getAllByOrder(id);
    }

    @GetMapping("/{apostilleId}")
    public Apostille get(@PathVariable int id, @PathVariable int apostilleId) {
        log.info("get for order {}, apostille {}", id, apostilleId);
        return apostilleService.get(id, apostilleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable int id, @RequestBody Apostille apostille) {
        log.info("create for order {}, apostille {}", id, apostille);
        apostilleService.create(apostille, id);
    }

    @PutMapping("/{apostilleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @PathVariable int apostilleId, @RequestBody Apostille apostille) {
        log.info("update apostille {}, data {}", apostilleId, apostille);
        apostilleService.update(apostille, id);
    }

    @DeleteMapping("/{apostilleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int apostilleId) {
        log.info("delete for order {}, apostille {}", id, apostilleId);
        apostilleService.delete(id, apostilleId);
    }
}
