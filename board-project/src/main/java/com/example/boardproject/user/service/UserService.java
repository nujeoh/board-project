package com.example.boardproject.user.service;

import com.example.boardproject.exception.exceptions.UserNotFoundException;
import com.example.boardproject.post.db.PostEntity;
import com.example.boardproject.post.db.PostRepository;
import com.example.boardproject.post.service.PostService;
import com.example.boardproject.user.db.UserEntity;
import com.example.boardproject.user.db.UserRepository;
import com.example.boardproject.user.model.UserResponseDTO;
import com.example.boardproject.user.model.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // ユーザーに関するデータベース操作を処理するリポジトリ
    private final UserConverter userConverter; // EntityをDtoに変換するためのコンバータ
    private final PostRepository postRepository; // 投稿に関するデータベース操作を処理するリポジトリ
    private final PostService postService; // EntityをDtoに変換するためのコンバータ

    // 指定されたアカウントが存在するかを確認するメソッド
    public boolean checkId(String account) {
        // アカウントの存在を確認して true または false に返す
        return userRepository.existsByAccount(account);
    }

    // ユーザー情報を登録するメソッド
    public void save(UserRequestDTO userRequestDTO) {

        // ユーザー情報を基にUserEntityを生成
        var entity = UserEntity.builder()
                .account(userRequestDTO.getAccount())
                .password(userRequestDTO.getPassword())
                .name(userRequestDTO.getName())
                .phone(userRequestDTO.getPhone())
                .email(userRequestDTO.getEmail1() + "@" + userRequestDTO.getEmail2())
                .regDate(LocalDateTime.now())
                .build();

        // 生成されたEntityをデータベースに登録
        userRepository.save(entity);
    }

    // ログイン処理をするメソッド
    public UserResponseDTO login(String account, String password) {
        // 指定されたアカウントとパスワードに該当するユーザーEntityを検索
        var entity = userRepository.findByAccountAndPassword(account, password);
        // 検索されたEntityをDtoに変換して返す
        return userConverter.toDto(entity);
    }

    // ユーザーを読み込むメソッド
    public UserResponseDTO readUser(Long id) {

        // 投稿情報を取得
        var entity = userRepository.findById(id).orElseThrow(
                // ユーザーがない場合、エラー発生
                () -> new UserNotFoundException("ユーザーが見つかりませんでした。")
        );

        // 取得した情報をDtoに変換して返す
        return userConverter.toDto(entity);
    }

    // ユーザーを修正するメソッド
    public void update(UserRequestDTO userRequestDTO, Long userId) {

        // 投稿情報を修正してアップデート
        userRepository.findById(userId)
                .map(user -> {
                    user.setName(userRequestDTO.getName());
                    user.setPhone(userRequestDTO.getPhone());
                    user.setEmail(userRequestDTO.getEmail1() + "@" + userRequestDTO.getEmail2());
                    userRepository.save(user);
                    return user;
                }).orElseThrow(
                    // ユーザーがない場合、エラー発生
                    () -> new UserNotFoundException("ユーザーが見つかりませんでした。")
                );
    }

    // ユーザーを削除するメソッド、
    @Transactional //削除中にエラーが発生した場合、ロールバック
    public void delete(Long userId) {

        // ユーザーを取得
        var userEntity = userRepository.findById(userId)
                .orElseThrow(
                        // ユーザーがない場合、エラー発生
                        () -> new UserNotFoundException("ユーザーが見つかりませんでした。")
                );

        // ユーザーが作成した投稿、コメント、返信コメントをすべて削除
        List<PostEntity> PostEntityList = postRepository.findByUserId(userId);
        for (PostEntity postEntity : PostEntityList) {
            postService.delete(postEntity.getId());
        }

        // ユーザーを削除
        userRepository.delete(userEntity);
    }
}
