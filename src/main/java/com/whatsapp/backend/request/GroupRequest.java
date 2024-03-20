package com.whatsapp.backend.request;

import com.whatsapp.backend.model.User;
import lombok.Data;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class GroupRequest {
    private String groupName;
    private String chat_image;
    private List<Long> member_ids = new ArrayList<>();
}
