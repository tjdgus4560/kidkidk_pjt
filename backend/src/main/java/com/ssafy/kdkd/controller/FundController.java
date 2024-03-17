package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.dto.fund.FundDto;
import com.ssafy.kdkd.domain.dto.fund.FundNewsDto;
import com.ssafy.kdkd.domain.dto.fund.FundReservationDto;
import com.ssafy.kdkd.domain.dto.fund.FundStatusDto;
import com.ssafy.kdkd.domain.dto.fund.RoiDto;
import com.ssafy.kdkd.domain.dto.fund.TransferDto;
import com.ssafy.kdkd.domain.entity.fund.Fund;
import com.ssafy.kdkd.domain.entity.fund.FundHistory;
import com.ssafy.kdkd.domain.entity.fund.FundNews;
import com.ssafy.kdkd.domain.entity.fund.FundReservation;
import com.ssafy.kdkd.domain.entity.fund.FundStatus;
import com.ssafy.kdkd.domain.entity.fund.Roi;
import com.ssafy.kdkd.repository.fund.FundHistoryRepository;
import com.ssafy.kdkd.repository.fund.FundNewsRepository;
import com.ssafy.kdkd.repository.fund.FundRepository;
import com.ssafy.kdkd.repository.fund.FundReservationRepository;
import com.ssafy.kdkd.repository.fund.FundStatusRepository;
import com.ssafy.kdkd.repository.fund.RoiRepository;
import com.ssafy.kdkd.service.fund.FundNewsService;
import com.ssafy.kdkd.service.fund.FundReservationService;
import com.ssafy.kdkd.service.fund.FundService;
import com.ssafy.kdkd.service.fund.FundStatusService;

import static com.ssafy.kdkd.domain.dto.fund.FundHistoryDto.mappingFundHistoryDto;
import static com.ssafy.kdkd.domain.dto.fund.FundNewsDto.mappingFundNewsDto;
import static com.ssafy.kdkd.domain.dto.fund.FundStatusDto.mappingFundStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fund")
public class FundController {

    private final FundService fundService;
    private final FundRepository fundRepository;
    private final FundReservationService fundReservationService;
    private final FundReservationRepository fundReservationRepository;
    private final FundStatusService fundStatusService;
    private final FundStatusRepository fundStatusRepository;
    private final RoiRepository roiRepository;
    private final FundHistoryRepository fundHistoryRepository;
    private final FundNewsService fundNewsService;
    private final FundNewsRepository fundNewsRepository;

    @PostMapping("/transfer")
    @Operation(summary = "코인계좌로 이체")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: transfer() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean isOk = fundService.transferToCoin(transferDto);

                if (!isOk) {
                    status = HttpStatus.NOT_ACCEPTABLE;
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/child/submit")
    @Operation(summary = "투자 항목 아이 선택")
    public ResponseEntity<?> submitChild(@RequestBody FundStatusDto fundStatusDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: submitChild() Enter");
            Long childId = fundStatusDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean isChild = true;
                boolean isRejected = fundStatusService.setFundStatus(fundStatusDto, isChild);

                if (isRejected) {
                    status = HttpStatus.NOT_ACCEPTABLE;
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/parent/submit")
    @Operation(summary = "투자 항목 부모 선택")
    public ResponseEntity<?> submitParent(@RequestBody FundStatusDto fundStatusDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: submitParent() Enter");
            Long childId = fundStatusDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean isChild = false;
                boolean isRejected = fundStatusService.setFundStatus(fundStatusDto, isChild);

                if (isRejected) {
                    status = HttpStatus.NO_CONTENT;
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/status/confirm/{childId}")
    @Operation(summary = "투자 상태 조회")
    public ResponseEntity<?> confirmStatus(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: confirmStatus() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<FundStatus> fundStatus = fundStatusRepository.findById(childId);
                if (fundStatus.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("FundStatus", mappingFundStatus(fundStatus.get()));
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/confirm/{childId}")
    @Operation(summary = "투자 항목 조회")
    public ResponseEntity<?> confirmFund(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: confirmFund() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<Fund> resultFund = fundRepository.findById(childId);
                if (resultFund.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("Fund", FundDto.mappingFundDto(resultFund.get()));
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/reservation/confirm/{childId}")
    @Operation(summary = "투자 예약 조회")
    public ResponseEntity<?> confirmReservation(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: confirmReservation() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<FundReservation> resultFundReservation = fundReservationRepository.findById(childId);
                if (resultFundReservation.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    FundReservationDto fundReservationDto = FundReservationDto.mappingFundReservationDto(
                        resultFundReservation.get());
                    resultMap.put("FundReservation", fundReservationDto);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/create")
    @Operation(summary = "투자 항목 생성")
    public ResponseEntity<?> createFund(@RequestBody FundReservationDto fundReservationDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            log.info("fund controller: createFund() Enter");
            Long childId = fundReservationDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean type = true;
                FundReservationDto result = fundReservationService.createFundReservation(fundReservationDto, type);

                if (result == null) {
                    status = HttpStatus.CONFLICT;
                } else {
                    resultMap.put("Fund", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/reservation/create")
    @Operation(summary = "투자 예약 생성")
    public ResponseEntity<?> createReservation(@RequestBody FundReservationDto fundReservationDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            log.info("fund controller: createReservation() Enter");
            Long childId = fundReservationDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean type = false;
                FundReservationDto result = fundReservationService.createFundReservation(fundReservationDto, type);

                if (result == null) {
                    status = HttpStatus.CONFLICT;
                } else {
                    resultMap.put("FundReservation", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/reservation/modify")
    @Operation(summary = "투자 예약 수정")
    public ResponseEntity<?> modifyReservation(@RequestBody FundReservationDto fundReservationDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: modifyReservation() Enter");
            Long childId = fundReservationDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                FundReservationDto result =
                    fundReservationService.modifyFundReservation(fundReservationDto);

                if (result == null) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("FundReservation", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/reservation/delete/{childId}")
    @Operation(summary = "투자 예약 삭제")
    public ResponseEntity<?> deleteReservation(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: deleteReservation() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                fundReservationRepository.deleteById(childId);
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/delete/{childId}")
    @Operation(summary = "투자 항목 제거")
    public ResponseEntity<?> deleteFund(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: deleteFund() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                FundReservationDto result = fundReservationService.deleteFundReservation(childId);

                if (result == null) {
                    status = HttpStatus.NO_CONTENT;
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/roi/{childId}")
    @Operation(summary = "투자 성공률 조회")
    public ResponseEntity<?> roi(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: roi() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<Roi> result = roiRepository.findById(childId);

                if (result.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    RoiDto roiDto = RoiDto.mappingRoiDto(result.get());
                    resultMap.put("roi", roiDto);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/history/confirm/{childId}")
    @Operation(summary = "투자 내역 조회")
    public ResponseEntity<?> history(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: history() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                List<FundHistory> fundHistories = fundHistoryRepository.findFundHistoriesByChildId(childId);

                if (fundHistories.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("FundHistory", mappingFundHistoryDto(fundHistories));
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/news/{childId}")
    @Operation(summary = "투자 뉴스 조회")
    public ResponseEntity<?> confirmFundNews(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: confirmFundNews() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                List<FundNews> fundNewsList = fundNewsRepository.findFundNewsByChildId(childId);

                if (fundNewsList.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("FundNews", mappingFundNewsDto(fundNewsList));
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/news")
    @Operation(summary = "투자 뉴스 등록")
    public ResponseEntity<?> createFundNews(@RequestBody FundNewsDto fundNewsDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            log.info("fund controller: createFundNews() Enter");
            Long childId = fundNewsDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                FundNewsDto result = fundNewsService.insertFundNews(fundNewsDto);

                if (result == null) {
                    status = HttpStatus.CONFLICT;
                } else {
                    resultMap.put("FundNews", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
