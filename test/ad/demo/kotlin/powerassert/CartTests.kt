package ad.demo.kotlin.powerassert

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.Ignore
import kotlin.test.assertEquals

class CartTests {

    @Test
    fun `new carts have different ids`() {
        val c1 = emptyCart()
        val c2 = emptyCart()
        assert(c1.cartId == c2.cartId)
    }

    @Test
    fun `empty cart has no items`() {
        assert(emptyCart().items.isEmpty())
    }

    @Test
    fun `with one item`() {
        val cart = emptyCart().withItem(Sku("product"))
        assertAll(
            "single item",
            { assertEquals(expected = Sku("product"), cart.items.single().sku) },
            { assertEquals(expected = Quantity(1), cart.items.single().quantity) }
        )
    }

    @Test
    fun `with item quantity greater 1`() {
        val cart = emptyCart().withItem(Sku("product"), Quantity(5))

        assertEquals(expected = Quantity(5), cart.itemBySku(Sku("product"))?.quantity)
    }

    @Test
    fun `with item quantity greater 1 (assertJ)`() {
        val cart = emptyCart().withItem(Sku("product"), Quantity(5))

        assertThat(cart.itemBySku(Sku("product"))?.quantity).isEqualTo(Quantity(5))
    }

    @Test
    fun `with multiple items`() {
        val cart = emptyCart()
            .withItem(Sku("product-1"))
            .withItem(Sku("product-2"))
            .withItem(Sku("product-3"))

        assertEquals(
            expected = listOf(Sku("product-1"), Sku("product-2"), Sku("product-3")),
            cart.items.map { it.sku }
        )
    }

    @Test
    fun `with multiple items (assertJ)`() {
        val cart = emptyCart()
            .withItem(Sku("product-1"))
            .withItem(Sku("product-2"))
            .withItem(Sku("product-3"))

        assertThat(cart.items.map { it.sku })
            .containsExactly(Sku("product-1"), Sku("product-2"), Sku("product-3"))
    }

    @Test
    @Ignore
    fun `with duplicate sku adds quantities`() {
        val cart = emptyCart()
            .withItem(Sku("product"), Quantity(3))
            .withItem(Sku("product"), Quantity(2))

        assertEquals(expected = Quantity(5), cart.itemBySku(Sku("product"))?.quantity)
    }

    @Test
    @Ignore
    fun `with duplicate sku adds quantities (assertJ)`() {
        val cart = emptyCart()
            .withItem(Sku("product"), Quantity(3))
            .withItem(Sku("product"), Quantity(2))

        assertThat(cart.itemBySku(Sku("product"))?.quantity).isEqualTo(Quantity(5))
    }

    @Test
    fun `custom assert has sku example`() {
        val cart = emptyCart()
            .withItem(Sku("product-1"))
            .withItem(Sku("product-2"))
            .withItem(Sku("product-3"))

        assertHasSku(cart, Sku("product-3"))
    }
}

fun Cart.itemBySku(sku: Sku) =
    items.firstOrNull { it.sku == sku }
//    items.singleOrNull { it.sku == sku }

// demo: custom assert
fun assertHasSku(cart: Cart, expectedSku: Sku, message: (() -> String)? = null) {
    if (cart.items.none { it.sku == expectedSku }) throw AssertionError(
        message?.invoke() ?: "Cart ${cart.cartId} has no item with SKU '$expectedSku'; existing SKUs: ${cart.items.map { it.sku }}"
    )
//    assertThat(cart.items)
//        .describedAs { message?.invoke() ?: "cart has item with SKU '$expectedSku'" }
//        .anyMatch { it.sku == expectedSku }
}
