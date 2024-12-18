package animal_shop.global.pay.controller;

import animal_shop.global.pay.dto.*;
import animal_shop.global.pay.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/kakaoReady")
    public ResponseEntity readyToKakaoPay(
            @RequestBody KakaoReadyRequest kakaoReadyRequest) {
        return ResponseEntity.ok().body(kakaoPayService.kakaoPayReady(kakaoReadyRequest));
    }
    /**
     * 결제 성공
     */
    @PostMapping("/kakaoSuccess")
    public ResponseEntity afterPayRequest(@RequestHeader(value = "Authorization") String token,
                                          @RequestBody KakaoSuccessRequest kakaoSuccessRequest) {

        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(kakaoSuccessRequest,token);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/kakaoCancel")
    public void cancel(@RequestParam(value = "orderId")Long orderId) {

        throw new IllegalArgumentException("pay cancel");
    }

    /**
     * 결제 실패
     */
    @GetMapping("/kakaoFail")
    public void fail(@RequestParam(value = "orderId")Long orderId) {

        throw new IllegalArgumentException("pay fail");
    }

    /**
     * 환불
     */
    @PostMapping("/kakaoRefund")
    public ResponseEntity refund(@RequestBody KakaoCancelRequest kakaoCancelRequest) {

        KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(kakaoCancelRequest);

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}