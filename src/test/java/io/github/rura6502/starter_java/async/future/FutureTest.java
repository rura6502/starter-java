package io.github.rura6502.starter_java.async.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.github.rura6502.starter_java.async.future.FutureTest.Helper.createFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * java.util.concurrent.Future
 *
 * 비동기 작업 수행 인터페이스. 결과물 반환이 가능하며 작업 취소와 완료확인이 가능
 *
 * - 외부에서 컨트롤하기 힘듬(취소만 가능)
 * - 결과를 get으로만 접근할 수 있기 때문에 결과를 활용한 비동기 처리 불가능
 * - 내부에서 발생한 exception에 대한 인지, 유기적인 처리가 불가능
 */
public class FutureTest {

  @Test
  void 기본_동작_테스트() throws Exception {
    var future = createFuture(1000);

    // 아직 취소되거나 끝나지 않았기 때문에 false 이다
    assertThat(future.isCancelled()).isFalse();
    assertThat(future.isDone()).isFalse();

    // 작업이 끝날 때 까지 작업 스레드는 block 된다
    var result = future.get();

    assertThat(result).isEqualTo(1);
    assertThat(future.isDone()).isTrue();
    assertThat(future.isCancelled()).isFalse(); // 취소된 것은 아니므로 false
  }

  @Test
  void cancel() throws Exception {
    var future = createFuture(9999);
    var result = future.cancel(true);

    assertThat(result).isTrue();

    // 취소할 수 없는 상황(이미 완료됬거나 취소됨)이면 false
    assertThat(future.cancel(true)).isFalse();

    assertThat(future.isDone()).isTrue(); // cancel 이지만 done이므로 true
    assertThat(future.isCancelled()).isTrue();
  }


  @Test
  void get_with_timeout() throws Exception {
    var future = createFuture(9999);

    assertThatThrownBy(() -> {
      // 무한 blocking을 방지하기 위해 timeout 시간을 설정 할 수 있다
      future.get(1, TimeUnit.SECONDS);
    }).isInstanceOf(TimeoutException.class);
  }

  static class Helper {
    public static Future<Integer> createFuture(long delay) {
      var executor = Executors.newSingleThreadExecutor();
      
      try {
        return executor.submit(() -> {
          Thread.sleep(delay);
          return 1;
        });
      } finally {
        executor.shutdown();
      }
    }
  }
}
