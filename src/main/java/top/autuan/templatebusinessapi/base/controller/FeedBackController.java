package top.autuan.templatebusinessapi.base.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.autuan.templatebusinessapi.base.entity.FeedBackReq;
import top.autuan.templatebusinesssupport.base.entity.Feedback;
import top.autuan.templatebusinesssupport.base.service.FeedBackService;
import top.autuan.web.Result;

import java.util.List;

@RequestMapping("/feedback")
@RestController
@RequiredArgsConstructor
public class FeedBackController {
    private final FeedBackService feedBackService;

    @SaCheckLogin
    @PostMapping("/detail")
    Result<Object> detail(@RequestBody FeedBackReq req) {
        Feedback feedback = feedBackService.detail(req.getId());
        return Result.ok(feedback);
    }

    @SaCheckLogin
    @PostMapping("/list")
    Result<Object> listFeedback(@RequestBody FeedBackReq req) {
        List<Feedback> feedbackList = feedBackService.feedBackList(req.getMemberId(), req.getStatus(), req.getFeedBackType());
        return Result.ok(feedbackList);
    }

    @SaCheckLogin
    @PostMapping("/change")
    Result changeFeedback(@RequestBody Feedback feedback) {
        feedBackService.changeFeedback(feedback);
        return Result.ok();
    }

    @SaCheckLogin
    @PostMapping("/del")
    Result changeDelFlag(@RequestBody FeedBackReq req) {
        feedBackService.changDelFlag(req.getId());
        return Result.ok();
    }

    @SaCheckLogin
    @PostMapping("/add")
    Result addData(@RequestBody Feedback feedback) {
        feedBackService.addData(feedback);
        return Result.ok();
    }


    @SaCheckLogin
    @PostMapping("/handler")
    Result dealFeedback(@RequestBody FeedBackReq req) {
        feedBackService.deal(req.getId(), req.getReply());
        return Result.ok();
    }

}
