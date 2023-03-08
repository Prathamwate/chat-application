package com.ChatApplication.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ChatApplication.chat.Model.Status;

public interface IStatusRepo extends JpaRepository<Status,Integer>{

}
