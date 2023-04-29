import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 6:30 PM [28-04-2023]
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {

        // Decodes Base64-encoded strings taken from the snapshotsxmls
        String base64EncodedValue = "eyJzdGF0ZVRva2VuIjoiMDBnOWZPU2dwSzJYVVhBb0o1a21hS0tUOHM5NEtvTDY4emxDWGlPWTdFIiwidHlwZSI6IlNFU1NJT05fU1RFUF9VUCIsImV4cGlyZXNBdCI6IjIwMjMtMDQtMDVUMTc6NDE6NTguMDAwWiIsInN0YXR1cyI6IlNVQ0NFU1MiLCJfZW1iZWRkZWQiOnsidXNlciI6eyJpZCI6IjAwdTh4cXM3eWp4ZGROZnBnNWQ3IiwicGFzc3dvcmRDaGFuZ2VkIjoiMjAyMy0wMy0zMVQxNjowOTo0My4wMDBaIiwicHJvZmlsZSI6eyJsb2dpbiI6IlNQeGRiYWxneWZldXdsbnBueEB5b3BtYWlsLmNvbSIsImZpcnN0TmFtZSI6bnVsbCwibGFzdE5hbWUiOm51bGwsImxvY2FsZSI6ImVuX1VTIiwidGltZVpvbmUiOiJBbWVyaWNhL0xvc19BbmdlbGVzIn19LCJ0YXJnZXQiOnsidHlwZSI6IkFQUCIsIm5hbWUiOiJzdGFnZS1vbnRzaWduaW5fZnJvb25saW5lYm9mZW5saWduZXBwZWFwcF8xIiwibGFiZWwiOiJGUk8gT25saW5lIC8gQk9GIGVuIGxpZ25lIFtQUEVdIEFQUCIsIl9saW5rcyI6eyJsb2dvIjp7Im5hbWUiOiJtZWRpdW0iLCJocmVmIjoiaHR0cHM6Ly9vazEyc3RhdGljLm9rdGFjZG4uY29tL2Fzc2V0cy9pbWcvbG9nb3MvZGVmYXVsdC42NzcwMjI4ZmIwZGFiNDlhMTY5NWVmNDQwYTUyNzliYi5wbmciLCJ0eXBlIjoiaW1hZ2UvcG5nIn19fSwiYXV0aGVudGljYXRpb24iOnsicHJvdG9jb2wiOiJTQU1MMi4wIiwiaXNzdWVyIjp7ImlkIjoiMG9hNG80aG50Z01Tc29JTzI1ZDciLCJuYW1lIjoiRlJPIE9ubGluZSAvIEJPRiBlbiBsaWduZSBbUFBFXSBBUFAiLCJ1cmkiOiJodHRwOi8vd3d3Lm9rdGEuY29tL2V4azRvNGhudGZ3ODFPbUNvNWQ3In19fSwiX2xpbmtzIjp7Im5leHQiOnsibmFtZSI6Im9yaWdpbmFsIiwiaHJlZiI6Imh0dHBzOi8vc3RhZ2Uuc2lnbmluLm9udGFyaW8uY2EvbG9naW4vc3RlcC11cC9yZWRpcmVjdD9zdGF0ZVRva2VuPTAwZzlmT1NncEsyWFVYQW9KNWttYUtLVDhzOTRLb0w2OHpsQ1hpT1k3RSIsImhpbnRzIjp7ImFsbG93IjpbIkdFVCJdfX0sImNhbmNlbCI6eyJocmVmIjoiaHR0cHM6Ly9zdGFnZS5zaWduaW4ub250YXJpby5jYS9hcGkvdjEvYXV0aG4vY2FuY2VsIiwiaGludHMiOnsiYWxsb3ciOlsiUE9TVCJdfX19fQ";
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedValue);
        String decodedValue = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println(decodedValue);
    }
}