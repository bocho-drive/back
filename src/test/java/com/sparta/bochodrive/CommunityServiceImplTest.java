package com.sparta.bochodrive;import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;

import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;


@ExtendWith(MockitoExtension.class)
@Slf4j
public class CommunityServiceImplTest {

    @Mock
    private CommunityRepository communityRepository;

    @InjectMocks
    private CommunityServiceImpl communityService;

    @Test
    public void testAddPost() throws Exception {
        User user = new User();
        user.setEmail("testUser");

        CommunityRequestDto requestDto = new CommunityRequestDto("Test Title", "Test Content", CategoryEnum.GENERAL, Arrays.asList("image1.jpg", "image2.jpg"));
        Community community = new Community(requestDto, user);


        community.setId(1L);
        when(communityRepository.save(any(Community.class))).thenReturn(community);

        log.info("Calling addPost method");
        CommunityResponseDto responseDto = communityService.addPost(requestDto, user);
        assertNotNull(responseDto);
        assertEquals("Test Title", responseDto.getTitle());
        assertEquals(1L, responseDto.getId());
    }

    @Test
    public void testGetAllPosts() throws Exception {
        Community community1 = new Community();
        Community community2 = new Community();
        when(communityRepository.findAll()).thenReturn(Arrays.asList(community1, community2));

        List<CommunityListResponseDto> posts = communityService.getAllPosts(null);
        assertNotNull(posts);
        assertEquals(2, posts.size());
    }

    @Test
    public void testGetPost() throws NotFoundException {
        Community community = new Community();
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        CommunityResponseDto responseDto = communityService.getPost(1L);
        assertNotNull(responseDto);
    }

    @Test
    public void testUpdatePost() throws Exception {
        Community community = new Community();
        CommunityRequestDto requestDto = new CommunityRequestDto("Updated Title", "Updated Content", CategoryEnum.GENERAL, Arrays.asList("image1.jpg", "image2.jpg"));
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        communityService.updatePost(1L, requestDto);

        verify(communityRepository).save(any(Community.class));
        assertEquals("Updated Title", community.getTitle());
    }

    @Test
    public void testDeletePost() throws Exception {
        Community community = new Community();
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        communityService.deletePost(1L);

        ArgumentCaptor<Community> communityCaptor = ArgumentCaptor.forClass(Community.class);
        verify(communityRepository).save(communityCaptor.capture());
        Community deletedCommunity = communityCaptor.getValue();

        assertTrue(deletedCommunity.isDeleteYN());
    }

    @Test
    public void testFindCommunityById() {
        Community community = new Community();
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        Community foundCommunity = communityService.findCommunityById(1L);
        assertNotNull(foundCommunity);
    }

    @Test
    public void testFindCommunityById_NotFound() {
        when(communityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            communityService.findCommunityById(1L);
        });

        String expectedMessage = ErrorCode.POST_NOT_FOUND.getMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}