package com.app.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Movies extends BaseEntity{
	private String mName;
	private String mDescription;
	private int mRating;
	private String imagePath;
	private String movieImageName;
}
