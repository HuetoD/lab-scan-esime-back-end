package ipn.esimecu.labscan.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipn.esimecu.labscan.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.OutputStream;

public final class SecurityConstant {

    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_SECRET = "zmmp$Bh[mUNDS59G.C;Rñ";
    public static final String JWT_ISSUER = "ISW_EQUIPO_4";
    public static final String JWT_AUTHORITIES = "roles";
    public static final long JWT_EXPIRATION_TIME_MILLIS = 600_000_000L;
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Parece que no has iniciado sesion";
    public static final String SUCCESS_ACCESS = "Inicio de sesion satisfactorio";

    public static final String [] PUBLIC_URLS = {
            "/auth/login",
            "/auth/reset-password",
            "/auth/ping",
            "/auth/encode-password",

            "/admin/ping",

            "/student/ping",
            "/student/get-labs",
            "/student/get-semesters",
            "/student/get-groups",
            "/student/get-groups-of-the-week",
            "/student/load-all-identifiers",
            "/student/find-student-by-qr",
            "/student/find-student-by-report-number",
            "/student/save-student",
            "/student/update-student"
    };

    public static void handleUnauthorized(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        new ObjectMapper().writeValue(outputStream, ErrorResponse.builder()
                                                                .message("Debes iniciar sesión")
                                                                .details("No estás autorizado para acceder a este recurso")
                                                                .build());
        outputStream.flush();
    }

}
