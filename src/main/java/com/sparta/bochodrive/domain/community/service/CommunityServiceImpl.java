package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import com.sparta.bochodrive.domain.imageS3.repository.ImageS3Repository;
import com.sparta.bochodrive.domain.imageS3.service.ImageS3Service;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {


    private final ImageS3Service imageS3Service;
    private final CommunityRepository communityRepository;
    private final CommonFuntion commonFuntion;
    private final ImageS3Repository imageS3Repository;

    @Override
    public Long addPost(CommunityRequestDto communityRequestDto, User user) {



        // 이미지 파일 리스트 가져오기
        List<MultipartFile> requestImages = communityRequestDto.getImage();

        Community community = Community.builder()
                .title((communityRequestDto.getTitle()))
                .content(communityRequestDto.getContent())
                .category(communityRequestDto.getCategory())
                .user(user)
                .build();

        Community savedCommunity = communityRepository.save(community);

        // 이미지 파일이 null이거나 비어 있지 않은지 확인
        if (requestImages != null && !requestImages.isEmpty()) {
            for (MultipartFile image : requestImages) {
                try {
                    String url = imageS3Service.upload(image);
                    String filename = imageS3Service.getFileName(url);

                    ImageS3 imageS3 = ImageS3.builder()
                            .uploadUrl(url)
                            .fileName(filename)
                            .community(savedCommunity)
                            .build();

                    imageS3Repository.save(imageS3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return savedCommunity.getId();
    }

    // 게시글 목록 조회
    @Override
    public Page<CommunityListResponseDto> getAllPosts(CategoryEnum category, int page, int size, String sortBy, boolean isAsc) {
        // 정렬 방향 설정
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Community> communityPage;

        // 카테고리별 목록 조회
        if (category == null) {
            communityPage = communityRepository.findAllByDeleteYNFalseOrderByCreatedAtDesc(pageable); // 카테고리 설정 안했을 때 전체글 목록
        } else {
            communityPage = communityRepository.findAllByCategoryAndDeleteYNFalse(category, pageable); // 카테고리별 글목록
        }

        // Community 엔티티를 CommunityListResponseDto로 변환
        return communityPage.map(CommunityListResponseDto::new);
    }

    //게시글 상세 조회
    @Override
    public CommunityResponseDto getPost(Long id, CustomUserDetails customUserDetails) {

        //존재하는 커뮤니티인지 확인 여부
        Community community=findCommunityById(id);

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(community.getId());

        //조회수 +1
        community.setViewCount(community.getViewCount()+1); //조회수 +1
        communityRepository.save(community);

        boolean isAuthor;
        if(customUserDetails!=null){
            isAuthor= customUserDetails.getUser().getId().equals(community.getUser().getId());
        }
        else{
            isAuthor=false;
        }

        return new CommunityResponseDto(community,isAuthor);
    }

    //게시글 수정
    @Override
    public Long updatePost(Long id, CommunityRequestDto communityRequestDto,User user) {


        Community community=findCommunityById(id);

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(community.getId());

        //게시글 community의 userId와 수정하려는 사람의 userId가 같은지에 관한 에외처리
        if(!community.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        }
        // 이미지 파일 리스트 가져오기
        List<MultipartFile> requestImages = communityRequestDto.getImage();

        if(requestImages!=null && !requestImages.isEmpty()){

            //새로운 이미지들 추가
            for(MultipartFile image : requestImages) {
                try{
                    String url=imageS3Service.upload(image);
                    String filename=imageS3Service.getFileName(url);

                    ImageS3 imageS3 = ImageS3.builder()
                            .uploadUrl(url)
                            .fileName(filename)
                            .community(community)
                            .build();

                    imageS3Repository.save(imageS3);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        community.update(communityRequestDto);//update
        Community save=communityRepository.save(community);
        return save.getId();
    }

    //게시글 삭제
    @Override
    public void deletePost(Long id, User user)  {

        Community community=findCommunityById(id);

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(community.getId());

        if(!user.getUserRole().equals(UserRole.ADMIN)){
            if(!community.getUser().getId().equals(user.getId())) {
                throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
            }
        }

        //진짜로 글을 삭제하는게 아니므로 -> 이미지도 삭제할 필요가 없음
        community.setDeleteYn(true);
        communityRepository.save(community);


    }

    //게시글 id 찾는 메소드
    public Community findCommunityById(Long id) {
        return communityRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(ErrorCode.POST_NOT_FOUND));
    }


}


