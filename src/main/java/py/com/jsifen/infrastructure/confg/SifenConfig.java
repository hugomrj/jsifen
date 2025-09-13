package py.com.jsifen.infrastructure.confg;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "siren")
public interface SifenConfig {
    String path();
    String idCsc();
    String csc();
    String ambiente();
}