// Movies.java
package com.app.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Movies extends BaseEntity {
    
    private String mName;
    private String mDescription;
    private int mRating;
    private String imagePath;
    private String movieImageName;
    
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<ShowtimesEntity> showTimes;
}
