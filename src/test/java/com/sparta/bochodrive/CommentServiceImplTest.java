package com.sparta.bochodrive;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.comment.service.CommentServiceImpl;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommunityRepository communityRepository;

    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    public void testAddComments() throws Exception {
        User user = new User();
        user.setEmail("testUser");

        Community community = new Community();
        community.setId(1L);

        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setCommunitiesId(1L);
        requestDto.setContent("Test Comment");

        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
        Comment comment = new Comment(requestDto, user, community);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentReponseDto responseDto = commentService.addComments(requestDto, user);
        assertNotNull(responseDto);
        assertEquals("Test Comment", responseDto.getContent());
    }

    @Test
    public void testGetComments() throws Exception {
        Community community = new Community();
        community.setId(1L);

        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> comments = Arrays.asList(comment1, comment2);

        when(commentRepository.findByCommunityId(1L)).thenReturn(comments);

        List<CommentReponseDto> responseDtos = commentService.getComments(1L);
        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());
    }

    @Test
    public void testUpdateComment() throws Exception {
        Comment comment = new Comment();
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("Updated Comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.updateComment(1L, requestDto);

        verify(commentRepository).save(any(Comment.class));
        assertEquals("Updated Comment", comment.getContent());
    }

    @Test
    public void testDeleteComment() throws Exception {
        Comment comment = new Comment();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L);

        verify(commentRepository).save(any(Comment.class));
        assertTrue(comment.isDeleteYN());
    }

    @Test
    public void testFindCommunityById() throws Exception {
        Community community = new Community();
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        Community foundCommunity = commentService.findCommunityById(1L);
        assertNotNull(foundCommunity);
    }

    @Test
    public void testFindCommentById() {
        Comment comment = new Comment();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.findCommentById(1L);
        assertNotNull(foundComment);
    }

    @Test
    public void testFindCommunityById_NotFound() {
        when(communityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            commentService.findCommunityById(1L);
        });

        String expectedMessage = ErrorCode.POST_NOT_FOUND.getMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindCommentById_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            commentService.findCommentById(1L);
        });

        String expectedMessage = ErrorCode.COMMENT_NOT_FOUND.getMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}