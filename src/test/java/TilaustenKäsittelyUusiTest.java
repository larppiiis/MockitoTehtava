import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TilaustenKäsittelyUusiTest {
    @Mock
    IHinnoittelija hinnoittelijaMock;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testaaKäsittelijäWithMockitoHinnoittelijaYli100() {
        float alkuSaldo = 200.0f;
        float listaHinta = 100.0f;
        float alennus = 20.0f;
        float loppuSaldo;

        if(listaHinta >= 100){
            loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + 5) / 100));
        } else {
            loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        }

        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus + 5);

        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));

        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock, atLeastOnce()).getAlennusProsentti(asiakas, tuote);
    }
    public void testaaKäsittelijäWithMockitoHinnoittelijaAlle100() {
        float alkuSaldo = 200.0f;
        float listaHinta = 99.9f;
        float alennus = 20.0f;
        float loppuSaldo;

        if(listaHinta >= 100){
            loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + 5) / 100));
        } else {
            loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        }

        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus + 5);

        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));

        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock, atLeastOnce()).getAlennusProsentti(asiakas, tuote);
    }
}