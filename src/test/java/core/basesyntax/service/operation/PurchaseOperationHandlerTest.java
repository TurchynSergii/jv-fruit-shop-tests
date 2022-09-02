package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PurchaseOperationHandlerTest {
    private FruitDao fruitDao;
    private OperationHandler handler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        handler = new PurchaseOperationHandler(fruitDao);
    }

    @Test
    public void handle_purchaseTransaction_Ok() {
        Storage.fruitsQuantity.put("banana", 15);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15);
        handler.handle(fruitTransaction);
        Integer expectedBanana = 0;
        Integer actualBanana = Storage.fruitsQuantity.get("banana");
        assertEquals(expectedBanana, actualBanana);
    }

    @Test (expected = RuntimeException.class)
    public void handle_emptyStorage_NotOk() {
        Storage.fruitsQuantity.put("banana", 14);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15);
        handler.handle(fruitTransaction);
    }

    @Test (expected = RuntimeException.class)
    public void handle_emptyTransaction_NotOk() {
        fruitTransaction = new FruitTransaction();
        handler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsQuantity.clear();
    }
}