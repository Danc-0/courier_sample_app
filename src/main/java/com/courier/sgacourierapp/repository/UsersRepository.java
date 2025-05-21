package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByPhoneNumber(String phoneNumber);

    List<UserEntity> findAllByRole(CourierEnums.UserRoles role);

    List<UserEntity> findAllByRoleAndStatus(CourierEnums.UserRoles role, String status);

    List<UserEntity> findAllByRoleAndDispatchStatus(CourierEnums.UserRoles role, CourierEnums.DispatchStatus dispatchStatus);
}
