package com.mycompany.treviska;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MaterialTagService {
    private MaterialRepository materialRepository;
    private MaterialTagRepository materialTagRepository;
    
    public MaterialTagsResponse createTags(){};
    
    public MaterialTagsResponse removeTags(){};
    
    public MaterialTagsResponse sortTagsBy(){};
    
    public MaterialTagsResponse getTagsBy(){};
    
    public MaterialTagsResponse countTags(){};
    
    public MaterialTagsResponse createTags(){};
    
    public MaterialTagsResponse getActiveTags(){}; //know the difference between this and getTagsBy
    
    public MaterialTagsResponse updateTags(){};
    
    
    
    
    
    
}
