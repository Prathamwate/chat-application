package com.ChatApplication.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ChatApplication.chat.Model.ChatHistory;
import java.util.*;

@Repository
public interface IChatHistory extends JpaRepository<ChatHistory, Integer> {

	  @Query(value = "Select * from tbl_chat_history where to_user_id = :senderId and status_id = 1",
	            nativeQuery = true)
	    List<ChatHistory> getChatsByUserId(int senderId);


	    @Query(value = "select * from tbl_chat_history where (to_user_id = :user1 and from_user_id = :user2)" +
	            "or (to_user_id  = :user2 and from_user_id = :user1) and status_id  = 1 order by created_date",
	            nativeQuery = true)
	    List<ChatHistory> getConversation(int user1, int user2);

}
