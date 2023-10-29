package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.announce.AnnounceDtoOut;
import ru.skypro.homework.dto.announce.CreateOrUpdateAd;
import ru.skypro.homework.entity.Announce;
import ru.skypro.homework.service.AnnounceService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AnnounceController {
    private final AnnounceService announceService;

    public AnnounceController(AnnounceService announceService) {
        this.announceService = announceService;
    }

    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Announce.class))
                    })
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<AnnounceDtoOut> results = announceService.getAll();
        long count = results.size();
        return ResponseEntity.status(HttpStatus.OK).body(Pair.of(count, results));
    }

    @Operation(summary = "Добавление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Announce.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(schema = @Schema(hidden = true))
                    })
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(@RequestPart CreateOrUpdateAd properties,
                                 @RequestPart MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(announceService.add(properties, image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Announce.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(schema = @Schema(hidden = true))
                    })
    })
    @GetMapping("{id]")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(announceService.get(id));
    }

    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(schema = @Schema(hidden = true))
                    })
    })
    @DeleteMapping("{id]")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        announceService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Обновление информации об объявлении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Announce.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(schema = @Schema(hidden = true))
                    })
    })
    @PatchMapping("{id]")
    public ResponseEntity<?> updateInfo(@PathVariable Long id,
                                        @RequestParam CreateOrUpdateAd property) {
        return ResponseEntity.status(HttpStatus.OK).body(announceService.updateInfo(id, property));
    }

    @Operation(summary = "Получение объявлений авторизированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Announce.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(schema = @Schema(hidden = true))
                    })
    })
    @GetMapping("me")
    public ResponseEntity<?> getUsersAll() {
        List<AnnounceDtoOut> results = announceService.getUsersAll();
        long count = results.size();
        return ResponseEntity.status(HttpStatus.OK).body(Pair.of(count, results));
    }

    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            schema = @Schema(implementation = Announce.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(schema = @Schema(hidden = true))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(schema = @Schema(hidden = true))
                    })
    })
    @PatchMapping(value = "{id]/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable Long id,
                                         @RequestPart MultipartFile image) {
        try {
            announceService.updateImage(id, image);
            return ResponseEntity.status(HttpStatus.OK).body("string");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
