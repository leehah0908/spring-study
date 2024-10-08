package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.ReplyPostRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyDetailResponseDTO;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper mapper;

    public void register(ReplyPostRequestDTO dto) {
        mapper.save(dto.toEntity());
    }

    public List<ReplyDetailResponseDTO> getList(int boardNo) {
        List<Reply> allList = mapper.findAll(boardNo);
        List<ReplyDetailResponseDTO> dtoList = new ArrayList<>();

        allList.forEach(reply -> dtoList.add(new ReplyDetailResponseDTO(reply)));

        return dtoList;

    }
}
