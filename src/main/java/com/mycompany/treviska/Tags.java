package com.mycompany.treviska;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;
@Entity(name="MATERIALTAGS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class Tags {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATERIALTAGID")
    private Long materialTagId;
    
    @Column(name = "MATERIALID", nullable = false, unique = true)
    private Long materialId;
    
    @Column(name = "TYPE", nullable = false, length = 50)
    private String type;
    
    // Store tags as JSON array
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "TAGS", columnDefinition = "jsonb")
    @Builder.Default
    private List<String> tags = new ArrayList<>();
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIALID", insertable = false, updatable = false)
    private Material material;
    
    
    public enum BookTag {
    FICTION("fiction"), ADVENTURE("adventure"), ACTION("action"), 
    HORROR("horror"), ROMANCE("romance"), WESTERN("western"), 
    COMEDY("comedy"), SCIENCE_FICTION("science-fiction"), 
    MYSTERY("mystery"), DARK_FANTASY("dark-fantasy"), 
    FANTASY("fantasy"), THRILLER("thriller"), 
    ROMANTIC_COMEDY("romantic-comedy"), DRAMA("drama");
    
     private final String value;
        BookTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
     public enum JournalTag {
        PEER_REVIEWED("peer-reviewed"), OPEN_ACCESS("open-access"),
        RESEARCH_ARTICLE("research-article"), REVIEW_ARTICLE("review-article"),
        CASE_STUDY("case-study"), META_ANALYSIS("meta-analysis"),
        SYSTEMATIC_REVIEW("systematic-review"), EDITORIAL("editorial");
        
        private final String value;
        JournalTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
     
     public enum NewspaperTag {
        FRONT_PAGE("front-page"), LOCAL_NEWS("local-news"), 
        NATIONAL_NEWS("national-news"), INTERNATIONAL("international"),
        POLITICS("politics"), BUSINESS("business"), SPORTS("sports"),
        OPINION("opinion"), EDITORIAL("editorial"),
        BREAKING_NEWS("breaking-news"), INVESTIGATIVE("investigative"),
        ANALYSIS("analysis"), COLUMN("column");
        
        private final String value;
        NewspaperTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
     public enum ResearchPaperTag {
        CONFERENCE_PAPER("conference-paper"), THESIS("thesis"),
        DISSERTATION("dissertation"), TECHNICAL_REPORT("technical-report"),
        WHITE_PAPER("white-paper"), WORKING_PAPER("working-paper"),
        PREPRINT("preprint"), HIGH_IMPACT("high-impact");
        
        private final String value;
        ResearchPaperTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
      public enum ReferenceMaterialTag {
        DICTIONARY("dictionary"), ENCYCLOPEDIA("encyclopedia"),
        ATLAS("atlas"), HANDBOOK("handbook"), DIRECTORY("directory"),
        MANUAL("manual"), SPECIFICATIONS("specifications");
        
        private final String value;
        ReferenceMaterialTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
    
    public enum DatabaseTag {
        RELATIONAL("relational"), NOSQL("nosql"), GRAPH("graph"),
        TIME_SERIES("time-series"), DOCUMENT("document"), 
        KEY_VALUE("key-value"), ACADEMIC("academic"), COMMERCIAL("commercial");
        
        private final String value;
        DatabaseTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
    
    public enum ArchiveTag {
        HISTORICAL("historical"), GOVERNMENT("government"), 
        INSTITUTIONAL("institutional"), PERSONAL("personal"),
        DIGITAL("digital"), PHYSICAL("physical");
        
        private final String value;
        ArchiveTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
    
    public enum DatasetTag {
        CSV("csv"), JSON("json"), XML("xml"), 
        STATISTICAL("statistical"), SURVEY("survey"), 
        EXPERIMENTAL("experimental"), OPEN_DATA("open-data");
        
        private final String value;
        DatasetTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
    
    public enum AudioBookTag {
        NARRATOR_PERFORMANCE("narrator-performance"), FULL_CAST("full-cast"), 
        ABRIDGED("abridged"), UNABRIDGED("unabridged"), 
        DRAMATIZED("dramatized"), ORIGINAL_AUDIO("original-audio");
        
        private final String value;
        AudioBookTag(String value) { this.value = value; }
        public String getValue() { return value; }
    }
      public static Set<String> getPredefinedTagsForType(String materialType) {
        return switch (materialType.toLowerCase()) {
            case "book" -> Arrays.stream(BookTag.values())
                    .map(BookTag::getValue)
                    .collect(Collectors.toSet());
            case "journal" -> Arrays.stream(JournalTag.values())
                    .map(JournalTag::getValue)
                    .collect(Collectors.toSet());
            case "magazine" -> Arrays.stream(MagazineTag.values())
                    .map(MagazineTag::getValue)
                    .collect(Collectors.toSet());
            case "newspaper" -> Arrays.stream(NewspaperTag.values())
                    .map(NewspaperTag::getValue)
                    .collect(Collectors.toSet());
            case "researchpaper" -> Arrays.stream(ResearchPaperTag.values())
                    .map(ResearchPaperTag::getValue)
                    .collect(Collectors.toSet());
            case "thesis" -> Arrays.stream(ResearchPaperTag.values())
                    .map(ResearchPaperTag::getValue)
                    .collect(Collectors.toSet());
            case "conferencepaper" -> Arrays.stream(ResearchPaperTag.values())
                    .map(ResearchPaperTag::getValue)
                    .collect(Collectors.toSet());
            case "referencematerial" -> Arrays.stream(ReferenceMaterialTag.values())
                    .map(ReferenceMaterialTag::getValue)
                    .collect(Collectors.toSet());
            case "database" -> Arrays.stream(DatabaseTag.values())
                    .map(DatabaseTag::getValue)
                    .collect(Collectors.toSet());
            case "archive" -> Arrays.stream(ArchiveTag.values())
                    .map(ArchiveTag::getValue)
                    .collect(Collectors.toSet());
            case "dataset" -> Arrays.stream(DatasetTag.values())
                    .map(DatasetTag::getValue)
                    .collect(Collectors.toSet());
            case "audiobook" -> Arrays.stream(AudioBookTag.values())
                    .map(AudioBookTag::getValue)
                    .collect(Collectors.toSet());
            default -> new HashSet<>();
        };
    }
    
    public void addTag(String tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        String normalizedTag = normalizeTag(tag);
        if (!normalizedTag.isEmpty() && !tags.contains(normalizedTag)) {
            tags.add(normalizedTag);
            updateTagCount();
        }
    }
    
    /**
     * Remove a tag manually
     */
    public void removeTag(String tag) {
        if (tags != null) {
            String normalizedTag = normalizeTag(tag);
            tags.remove(normalizedTag);
            updateTagCount();
        }
    }
      public MaterialTags(Long materialId, String type) {
        this.materialId = materialId;
        this.type = type.toLowerCase();
        this.tags = new ArrayList<>();
        this.tagCount = 0;
        this.isActive = true;
    }
    
    public MaterialTags(Long materialId, String type, List<String> tags) {
        this(materialId, type);
        setTags(tags);
    }
        
}
