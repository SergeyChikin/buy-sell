package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.announce.CreateOrUpdateAd;
import ru.skypro.homework.entity.Announce;
import ru.skypro.homework.dto.announce.AnnounceDto;
import ru.skypro.homework.dto.announce.AnnounceDtoOut;
import ru.skypro.homework.mapping.AnnounceMapper;
import ru.skypro.homework.repository.AnnounceRepository;
import ru.skypro.homework.service.AnnounceService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnounceServiceImpl implements AnnounceService {
    private final AnnounceRepository announceRepository;
    private final AnnounceMapper announceMapper;

    public AnnounceServiceImpl(AnnounceRepository announceRepository, AnnounceMapper announceMapper) {
        this.announceRepository = announceRepository;
        this.announceMapper = announceMapper;
    }

    /**
     *
     * the method returns all announces
     */
    @Override
    public List<AnnounceDtoOut> getUsersAll() {
        return announceRepository.findAll().stream()
                .map(announceMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * the method returns all user announces
     */

    @Override
    public List<AnnounceDtoOut> getAll() {
        //TODO Поставил заглушку на получение объявлений пользователя
        return announceRepository.findAll().stream()
                .map(announceMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * the method returns announce by id
     */

    @Override
    public AnnounceDto get(Integer id) {
        Announce announce = announceRepository.findById(id)
                //TODO Заменить на логер
                .orElseThrow(RuntimeException::new);
        return announceMapper.mapToAnnounceDto(announce);
    }

    /**
     *
     * the method add announce
     */
    @Override
    public AnnounceDtoOut add(CreateOrUpdateAd properties, MultipartFile image) throws IOException {
        AnnounceDto announceDtoIn = new AnnounceDto();
        //TODO Доставать pk пользователя, фио, email и номер телефона
//        announceDtoIn.setPk(0);
//        announceDtoIn.setAuthorFirstName(null);
//        announceDtoIn.setAuthorLastName(null);
//        announceDtoIn.setDescription(properties.getDescription());
//        announceDtoIn.setEmail(null);
//        announceDtoIn.setImage(Arrays.toString(image.getBytes()));
//        announceDtoIn.setPhone(null);
//        announceDtoIn.setPrice(properties.getPrice());
//        announceDtoIn.setTitle(properties.getTitle());
        return announceMapper.toDTO(announceRepository.save(announceMapper.createdAd(properties, null, null))); //TODO заменить null на путь к картинке и автора объявления
    }

    /**
     *
     * the method update announce info
     */

    @Override
    public AnnounceDtoOut updateInfo(Integer id, CreateOrUpdateAd property) {
        //TODO Заменить на логер
        Announce announce = announceRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        announce.setDescription(property.getDescription());
        announce.setTitle(announce.getTitle());
        announce.setPrice(property.getPrice());
        return announceMapper.toDTO(announceRepository.save(announce));
    }

    /**
     *
     * the method update announce image
     */
    @Override
    public void updateImage(Integer id, MultipartFile image) throws IOException {
        Announce announce = announceRepository.findById(id)
                //TODO Заменить на логер
                .orElseThrow(RuntimeException::new);
        announce.setImage(Arrays.toString(image.getBytes()));
    }

    /**
     *
     * the method delete announce
     */
    @Override
    public void delete(Integer id) {
        announceRepository.delete(
                //TODO Заменить на логер
                announceRepository.findById(id).orElseThrow(RuntimeException::new)
        );
    }
}
