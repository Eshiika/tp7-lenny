package fr.ekod.cda.ja.tp7.service;

import fr.ekod.cda.ja.tp7.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.tp7.dto.director.DirectorDTO;
import fr.ekod.cda.ja.tp7.entity.Director;
import fr.ekod.cda.ja.tp7.exception.ResourceNotFoundException;
import fr.ekod.cda.ja.tp7.mapper.DirectorMapper;
import fr.ekod.cda.ja.tp7.repository.DirectorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public DirectorService(DirectorRepository directorRepository, DirectorMapper directorMapper) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
    }

    @Transactional
    public DirectorDTO createDirector(CreateDirectorDTO dto) {
        Director director = directorMapper.toEntity(dto);
        Director savecDirector = directorRepository.save(director);
        return directorMapper.toDto(savecDirector);
    }

    public List<DirectorDTO> findAll() {
        return directorRepository.findAll()
                .stream()
                .map(directorMapper::toDto)
                .toList();
    }

    public DirectorDTO findById(Integer id) {
        return directorRepository.findById(id)
                .map(directorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Director", id));
    }

    @Transactional
    public DirectorDTO updateDirector(Integer id, CreateDirectorDTO dto) {
        return directorRepository.findById(id)
                .map(director -> {
                    director.setFirstName(dto.firstName());
                    director.setLastName(dto.lastName());
                    director.setNationality(dto.nationality());
                    director.setBirthDate(dto.birthDate());
                    return directorMapper.toDto(directorRepository.save(director));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Director", id));
    }

    @Transactional
    public void deleteDirector(Integer id) {
        directorRepository.deleteById(id);
    }

}
