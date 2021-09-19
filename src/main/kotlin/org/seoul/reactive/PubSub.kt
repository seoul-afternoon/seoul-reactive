package org.seoul.reactive

import java.util.concurrent.Flow.*

class PubSub {

    fun howToWorkPubSub() {
        val subscriber = object : Subscriber<String> {
            override fun onSubscribe(subscription: Subscription) {
                subscription.request(1)
            }

            override fun onNext(item: String) {
                println("next on $item")
            }

            override fun onError(throwable: Throwable?) {
                println("error $throwable")
            }

            override fun onComplete() {
                println("done")
            }
        }

        val publisher = object : Publisher<String> {

            val items = generateSequence("1") { i -> i + 1 }.take(500).iterator()

            override fun subscribe(subscriber: Subscriber<in String>) {
                try {
                    while (items.hasNext()) {
                        subscriber.onNext(items.next())
                    }
                    subscriber.onComplete()
                } catch (e: Exception) {
                    subscriber.onError(e)
                }
            }
        }
        publisher.subscribe(subscriber)
    }
}

fun main() {
    PubSub().howToWorkPubSub()
}