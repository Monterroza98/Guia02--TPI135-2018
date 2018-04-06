package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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


public class TipoResponsableFacadeTest {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("mantenimientoTestPU");
        em = emf.createEntityManager();

    }

    @Test
    public void crearTest() {
        TipoResponsable tipo1 = new TipoResponsable();
        tipo1.setNombreTipoResponsable("Tipo Responsable 1");
        tipo1.setActivo(true);

        TipoResponsable tipo2 = new TipoResponsable();
        tipo2.setNombreTipoResponsable("Tipo Responsable 2");
        tipo2.setDetalleTipoResponsable("detalle");
        tipo2.setActivo(true);

        TipoResponsable tipo3 = new TipoResponsable();

        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);

        trf.getEntityManager().getTransaction().begin();

        boolean test1 = trf.crear(null);
        boolean test2 = trf.crear(tipo1);
        boolean test3 = trf.crear(tipo2);

        trf.getEntityManager().getTransaction().commit();
        assertFalse(test1);
        assertTrue(test2);
        assertTrue(test3);
        assertEquals(2, trf.findAll().size());

    }

    @Test
    public void modifyTest() {
        TipoResponsable tr = new TipoResponsable();
        tr.setIdTipoResponsable(1);
        tr.setNombreTipoResponsable("Nuevo Tipo Responsable");
        tr.setDetalleTipoResponsable("detalle");
        tr.setActivo(true);

        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);
        trf.getEntityManager().getTransaction().begin();
        TipoResponsable t = trf.edit(tr);
        trf.getEntityManager().getTransaction().commit();
        assertEquals(tr, t);
        assertEquals(2, trf.findAll().size());
    }

    @Test
    public void eliminarTest() {

        TipoResponsable tr = new TipoResponsable();
        tr.setIdTipoResponsable(1);
        tr.setNombreTipoResponsable("Tipo Responsable 1");
        tr.setActivo(true);

        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", em);

        trf.getEntityManager().getTransaction().begin();
        boolean test1 = trf.eliminar(tr);
        boolean test2 = trf.eliminar(null);

        List<TipoResponsable> findAll = trf.findAll();

        trf.getEntityManager().getTransaction().commit();

        assertTrue(test1);
        assertFalse(test2);
        assertEquals(1, findAll.size());
    }

    @AfterClass
    public static void tearDown() {
        em.clear();
        em.close();
        emf.close();
    }

}
