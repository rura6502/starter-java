package io.github.rura6502.starter_java.async.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * java.util.CompletionStage
 *
 * 비동기 작업의 파이프라인을 구성하기 위한 스펙 제공. 콜백 처리 포함
 */
@Slf4j
public class CompletionStageTest {

  void thenAccept_반환값이_없다() {
    CompletableFuture.supplyAsync(() -> {

    })
  }
}
