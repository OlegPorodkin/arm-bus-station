package ru.porodkin.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.Passport;
import ru.porodkin.repository.PassportRepository;
import ru.porodkin.service.PassportService;
import ru.porodkin.service.dto.PassportDTO;
import ru.porodkin.service.mapper.PassportMapper;

/**
 * Service Implementation for managing {@link Passport}.
 */
@Service
@Transactional
public class PassportServiceImpl implements PassportService {

    private final Logger log = LoggerFactory.getLogger(PassportServiceImpl.class);

    private final PassportRepository passportRepository;

    private final PassportMapper passportMapper;

    public PassportServiceImpl(PassportRepository passportRepository, PassportMapper passportMapper) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
    }

    @Override
    public PassportDTO save(PassportDTO passportDTO) {
        log.debug("Request to save Passport : {}", passportDTO);
        Passport passport = passportMapper.toEntity(passportDTO);
        passport = passportRepository.save(passport);
        return passportMapper.toDto(passport);
    }

    @Override
    public Optional<PassportDTO> partialUpdate(PassportDTO passportDTO) {
        log.debug("Request to partially update Passport : {}", passportDTO);

        return passportRepository
            .findById(passportDTO.getId())
            .map(
                existingPassport -> {
                    passportMapper.partialUpdate(existingPassport, passportDTO);
                    return existingPassport;
                }
            )
            .map(passportRepository::save)
            .map(passportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassportDTO> findAll() {
        log.debug("Request to get all Passports");
        return passportRepository.findAll().stream().map(passportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the passports where Passenger is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PassportDTO> findAllWherePassengerIsNull() {
        log.debug("Request to get all passports where Passenger is null");
        return StreamSupport
            .stream(passportRepository.findAll().spliterator(), false)
            .filter(passport -> passport.getPassenger() == null)
            .map(passportMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PassportDTO> findOne(Long id) {
        log.debug("Request to get Passport : {}", id);
        return passportRepository.findById(id).map(passportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passport : {}", id);
        passportRepository.deleteById(id);
    }
}
