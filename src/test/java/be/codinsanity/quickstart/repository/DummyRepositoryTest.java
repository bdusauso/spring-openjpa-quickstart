package be.codinsanity.quickstart.repository;

import be.codinsanity.quickstart.domain.Dummy;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class DummyRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DummyRepository dao;

    @Test
    public void testSave() {
        Dummy dummy = new Dummy();
        dao.save(dummy);
        assertEquals(1, countRowsInTable("dummy"));
    }
}
