package app.dto;

import java.util.Arrays;

public enum PreventiveActionStatus {
    /**
     * Черновик
     */
    BLUEPRINT(0) {
        @Override
        public String toString() {
            return "Blueprint";
        }
    },
    /**
     * Новое
     */
    NEW(1) {
        @Override
        public String toString() {
            return "New";
        }
    },
    /**
     * Опубликовано(Разработано)
     */
    PUBLISHED(2) {
        @Override
        public String toString() {
            return "Published";
        }
    },
    /**
     * В работе
     */
    PROGRESS(3) {
        @Override
        public String toString() {
            return "Progress";
        }
    },
    /**
     * Удалено
     */
    DELETED(4) {
        @Override
        public String toString() {
            return "Deleted";
        }
    },
    /**
     * Отменено
     */
    CANCELED(5) {
        @Override
        public String toString() {
            return "Canceled";
        }
    },
    /**
     * Оценка результативности
     */
    EVALUATION(6) {
        @Override
        public String toString() {
            return "Evaluation";
        }
    },
    /**
     * Результативно
     */
    EFFECTIVE(7) {
        @Override
        public String toString() {
            return "Effective";
        }
    },
    /**
     * Не результативно
     */
    NOT_EFFECTIVE(8){
        @Override
        public String toString() {
            return "NotEffective";
        }
    },
    /**
     * Ожидает закрепления
     */
    WAIT_FIX(9) {
        @Override
        public String toString() {
            return "WaitFix";
        }
    },
    /**
     * Закреплено
     */
    FIXED(10) {
        @Override
        public String toString() {
            return "Fixed";
        }
    },
    /**
     * Без закрепления
     */
    NOT_FIXED(11) {
        @Override
        public String toString() {
            return "NotFixed";
        }
    };

    public final int id;

    PreventiveActionStatus(int id) {
        this.id = id;
    }

    public static PreventiveActionStatus parseOrder(Integer id) {
        return Arrays.stream(PreventiveActionStatus.values())
                .filter(status -> status.id == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                                String.format(
                                        "Fail to find PreventiveActionStatus with order [%s]",
                                        id
                                )
                        )
                );
    }
}
