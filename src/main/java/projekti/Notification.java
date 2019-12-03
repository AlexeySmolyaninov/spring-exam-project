package projekti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Alexey Smolyaninov
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Notification {
    private boolean goodMsg;
    private String msg;
}
