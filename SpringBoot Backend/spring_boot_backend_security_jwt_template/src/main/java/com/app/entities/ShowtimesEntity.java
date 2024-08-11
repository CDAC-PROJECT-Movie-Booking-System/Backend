// ShowtimesEntity.java
package com.app.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "showtimes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShowtimesEntity extends BaseEntity {

    private LocalTime showStartTime;
    private LocalTime showEndTime;
    private LocalDate showDate;
    
    @ManyToOne
    @JoinColumn(name = "movie_id")  // This is the field that the mappedBy attribute in Movies refers to
    private Movies movie;
    
    @OneToMany(mappedBy = "showtime", fetch = FetchType.EAGER)
    private Set<SeatEntity> seats;
    
    


}
