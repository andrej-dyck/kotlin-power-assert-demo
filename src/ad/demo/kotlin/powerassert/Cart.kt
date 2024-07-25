package ad.demo.kotlin.powerassert

import java.util.*

data class Cart(val cartId: ShortId, val items: List<CartItem>) {

    fun withItem(sku: Sku, quantity: Quantity? = null) =
        copy(items = items + CartItem(itemId = ShortId(), sku, quantity ?: Quantity(1)))
}

fun emptyCart() = Cart(cartId = ShortId(), items = emptyList())

data class CartItem(val itemId: ShortId, val sku: Sku, val quantity: Quantity)

@JvmInline
value class Sku(private val asString: String) {
    init {
        require(asString.isNotBlank())
    }
    override fun toString() = asString
}

@JvmInline
value class Quantity(private val amount: Int) {
    init {
        require(amount > 0)
    }
    override fun toString() = amount.toString()
}

@JvmInline
value class ShortId(private val id: String) {
    constructor() : this(UUID.randomUUID().toString().take(8))

    override fun toString() = id
}
