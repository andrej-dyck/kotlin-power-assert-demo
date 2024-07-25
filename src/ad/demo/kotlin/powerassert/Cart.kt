package ad.demo.kotlin.powerassert

import java.util.*

data class Cart(val cartId: ShortId, val items: List<CartItem>) {

    fun withItem(sku: Sku, quantity: Quantity? = null) =
        copy(items = items + CartItem(itemId = ShortId(), sku, quantity ?: Quantity(1)))

    override fun toString() = "Cart($cartId, $items)"
}

fun emptyCart() = Cart(cartId = ShortId(), items = emptyList())

data class CartItem(val itemId: ShortId, val sku: Sku, val quantity: Quantity) {

    override fun toString() = "Item($itemId, $sku, $quantity)"
}

@JvmInline
value class Sku(private val asString: String) {
    init {
        require(asString.isNotBlank())
    }
    override fun toString() = "Sku('$asString')"
}

@JvmInline
value class Quantity(private val amount: Int) {
    init {
        require(amount > 0)
    }
    override fun toString() = "Quantity($amount)"
}

@JvmInline
value class ShortId(private val id: String) {
    constructor() : this(UUID.randomUUID().toString().take(8))

    override fun toString() = "Id('$id')"
}
