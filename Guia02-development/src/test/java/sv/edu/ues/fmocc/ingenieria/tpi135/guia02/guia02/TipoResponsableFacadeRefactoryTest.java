package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.powermock.reflect.Whitebox;
import sv.edu.ues.fmocc.ingenieria.tpi135.mantenimiento.mantenimientolib.entity.TipoResponsable;

/**
 *
 * @author ale
 */
public class TipoResponsableFacadeRefactoryTest {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("mantenimientoTestPU");
        em = emf.createEntityManager();

    }

    @Test
    public void when_creating_null_tipo_responsable_expect_false() {
        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);
        trf.getEntityManager().getTransaction().begin();

        boolean result = trf.crear(null);

        assertEquals(0, trf.findAll().size());
        assertFalse(result);
    }

    @Test
    public void when_creating_new_tipo_responsable_expect_true() {
        TipoResponsable nuevoTipoResponsable = new TipoResponsable();
        nuevoTipoResponsable.setNombreTipoResponsable("Test Tipo Responsable");
        nuevoTipoResponsable.setDetalleTipoResponsable("Algun detalle de prueba");
        nuevoTipoResponsable.setActivo(true);

        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);

        trf.getEntityManager().getTransaction().begin();
       
        boolean result = trf.crear(nuevoTipoResponsable);

        assertTrue(result);
        assertEquals(1, trf.findAll().size());
    }

    @Test
    public void when_modify_valid_tipo_responsable_expect_true() {
        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);
        trf.getEntityManager().getTransaction().begin();
        trf.getEntityManager().persist(new TipoResponsable(null, "test tipo responsable", true));

        TipoResponsable expected = new TipoResponsable(1, "changed tipo responsable", false);
        TipoResponsable modified = new TipoResponsable(1, "changed tipo responsable", false);

        TipoResponsable result = trf.edit(expected);

        assertNotNull(result.getIdTipoResponsable());
        assertEquals(result.getNombreTipoResponsable(), expected.getNombreTipoResponsable());
    }

    @Test
    public void when_delete_null_tipo_responsable_expect_false() {
        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);
        trf.getEntityManager().getTransaction().begin();
        boolean result = trf.eliminar(null);
        assertEquals(0, trf.findAll().size());
        assertFalse(result);
    }

    @Test
    public void when_delete_valid_tipo_responsable_expect_true() {
        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);
        trf.getEntityManager().getTransaction().begin();
        trf.getEntityManager().persist(new TipoResponsable(null, "test tipo responsable", true));
        TipoResponsable entity = new TipoResponsable(1);
        boolean result = trf.eliminar(entity);
        assertTrue(result);
    }

    @After
    public void cleanup() {
        em.getTransaction().rollback();
    }

    @AfterClass
    public static void tearDown() {
        em.clear();
        em.close();
        emf.close();
    }

}
