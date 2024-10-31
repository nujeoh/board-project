package com.example.boardproject.exception.handler;

import com.example.boardproject.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 一般的な例外を処理するメソッド
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model){
        model.addAttribute("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reasonPhrase", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("message", "不明なエラーが発生しました。");
        return "error";
    }

    // バリデーションエラーを処理するメソッド
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        String errorMessage = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("不明な検証エラー");

        model.addAttribute("statusCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reasonPhrase", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", "検証エラー : " + errorMessage);
        return "error";
    }

    // アクセス権限のエラーを処理するメソッド
    @ExceptionHandler(NoAccessException.class)
    public String handleNoAccessException(NoAccessException ex, Model model) {
        model.addAttribute("statusCode", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reasonPhrase", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    // ユーザーが見つからない場合の例外を処理するメソッド
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reasonPhrase", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    // 投稿が見つからない場合の例外を処理するメソッド
    @ExceptionHandler(PostNotFoundException.class)
    public String handlePostNotFoundException(PostNotFoundException ex, Model model) {
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reasonPhrase", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    // 返信が見つからない場合の例外を処理するメソッド
    @ExceptionHandler(ReplyNotFoundException.class)
    public String handleReplyNotFoundException(ReplyNotFoundException ex, Model model) {
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reasonPhrase", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
