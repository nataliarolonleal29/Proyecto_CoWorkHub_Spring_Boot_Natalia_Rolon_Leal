package com.campuslands.proyecto_spring_boot_coworking.presentation.mapper;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.UserAccountResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    public UserAccountResponseDTO toDTO(UserAccount userAccount){

        Long memberId = null;

        if (userAccount.getMember() != null){
            memberId = userAccount.getMember().getId();
        }

        return new UserAccountResponseDTO(userAccount.getId(), userAccount.getUsername(), userAccount.getRole(), memberId);
    }
}
