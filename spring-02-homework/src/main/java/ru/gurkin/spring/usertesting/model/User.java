package ru.gurkin.spring.usertesting.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
/**
 * @author digurkin
 * Класс пользователя. Содержит данные об имени и фамилии
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
	@NonNull
	private String userName;
	@NonNull
	private String userSoname;
}
