package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CerealStorageImplTest {

    @Test
    fun `should throw if containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-4f, 10f)
        }
    }

    @Test
    fun `should throw if storageCapacity less then containerCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(40f, 10f)
        }
    }

    @Test
    fun `should not throw if storageCapacity equal containerCapacity`() {
        assertDoesNotThrow {
            CerealStorageImpl(40f, 40f)
        }
    }

    @Test
    fun `addCereal should throw if a negative value is passed`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(10f, 20f).addCereal(Cereal.PEAS, -1f)
        }
    }

    @Test
    fun `addCereal should not throw if zero is passed`() {
        assertDoesNotThrow {
            CerealStorageImpl(10f, 20f).addCereal(Cereal.PEAS, 0f)
        }
    }

    @Test
    fun `addCereal should not throw if a positive number is passed`() {
        assertDoesNotThrow {
            CerealStorageImpl(10f, 20f).addCereal(Cereal.PEAS, 1f)
        }
    }

    @Test
    fun `addCereal should throw if the storage does not allow for another container`() {
        assertThrows(IllegalStateException::class.java) {
            val cerealStorageImpl = CerealStorageImpl(10f, 15f)
            cerealStorageImpl.addCereal(Cereal.PEAS, 10f)
            cerealStorageImpl.addCereal(Cereal.RICE, 10f)
        }
    }

    @Test
    fun `addCereal should return the amount of remaining cereal if the container is full`() {
        assertEquals(
            CerealStorageImpl(10f, 20f).addCereal(Cereal.PEAS, 11f),
            1f,
            0.01f
        )
    }

    @Test
    fun `addCereal should return 0 if the container is not full`() {
        assertEquals(
            CerealStorageImpl(10f, 20f).addCereal(Cereal.PEAS, 9f),
            0f,
            0.01f
        )
    }

    @Test
    fun `addCereal should return all if the container is completely full`() {
        val cerealStorageImpl = CerealStorageImpl(10f, 10f)
        cerealStorageImpl.addCereal(Cereal.PEAS, 10f)
        assertEquals(
            cerealStorageImpl.addCereal(Cereal.PEAS, 9f),
            9f,
            0.01f
        )
    }

    @Test
    fun `getCereal should throw if a negative value is passed`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(10f, 20f).getCereal(Cereal.PEAS, -1f)
        }
    }

    @Test
    fun `getCereal should not throw if zero is passed`() {
        assertDoesNotThrow {
            CerealStorageImpl(10f, 20f).getCereal(Cereal.PEAS, 0f)
        }
    }

    @Test
    fun `getCereal should not throw if a positive number is passed`() {
        assertDoesNotThrow {
            CerealStorageImpl(10f, 20f).getCereal(Cereal.PEAS, 1f)
        }
    }

    @Test
    fun `getCereal should return zero if there is no cereal`() {
        assertEquals(
            CerealStorageImpl(10f, 20f).getCereal(Cereal.PEAS, 5f),
            0f,
            0.01f
        )
    }

    @Test
    fun `getCereal should return amount of cereal received`() {
        val cerealStorageImpl = CerealStorageImpl(10f, 20f)
        cerealStorageImpl.addCereal(Cereal.PEAS, 10f)
        assertEquals(
            cerealStorageImpl.getCereal(Cereal.PEAS, 9f),
            9f,
            0.01f
        )
    }

    @Test
    fun `getCereal should return the remainder`() {
        val cerealStorageImpl2 = CerealStorageImpl(10f, 20f)
        cerealStorageImpl2.addCereal(Cereal.PEAS, 5f)
        assertEquals(
            cerealStorageImpl2.getCereal(Cereal.PEAS, 9f),
            5f,
            0.01f
        )
    }

    @Test
    fun `removeContainer should return false if the container is not empty`() {
        val cerealStorageImpl = CerealStorageImpl(10f, 20f)
        cerealStorageImpl.addCereal(Cereal.PEAS, 1f)
        assertFalse(
            cerealStorageImpl.removeContainer(Cereal.PEAS)
        )
    }

    @Test
    fun `removeContainer should return true if the container is destroyed`() {
        val cerealStorageImpl2 = CerealStorageImpl(10f, 20f)
        cerealStorageImpl2.addCereal(Cereal.RICE, 1f)
        cerealStorageImpl2.getCereal(Cereal.RICE, 1f)
        assertTrue(
            cerealStorageImpl2.removeContainer(Cereal.RICE)
        )
    }

    @Test
    fun `removeContainer should return false if the container is not present`() {
        val cerealStorageImpl3 = CerealStorageImpl(10f, 20f)
        assertFalse(
            cerealStorageImpl3.removeContainer(Cereal.RICE)
        )
    }

    @Test
    fun `getAmount should return zero if there is no such container`() {
        assertEquals(
            CerealStorageImpl(10f, 20f).getAmount(Cereal.PEAS),
            0f,
            0.01f
        )
    }

    @Test
    fun `getAmount should return the amount of cereal stored in the container`() {
        val cerealStorageImpl = CerealStorageImpl(10f, 20f)
        cerealStorageImpl.addCereal(Cereal.PEAS, 1f)
        assertEquals(
            cerealStorageImpl.getAmount(Cereal.PEAS),
            1f,
            0.01f
        )
    }

    @Test
    fun `getCereal should decrease the amount of cereal, and getAmount should return the remainder`() {
        val cerealStorageImpl2 = CerealStorageImpl(10f, 20f)
        cerealStorageImpl2.addCereal(Cereal.PEAS, 5f)
        cerealStorageImpl2.getCereal(Cereal.PEAS, 3f)
        assertEquals(
            cerealStorageImpl2.getAmount(Cereal.PEAS),
            2f,
            0.01f
        )
    }

    @Test
    fun `getSpace should return the remaining space in the container`() {
        val cerealStorageImpl = CerealStorageImpl(10f, 20f)
        cerealStorageImpl.addCereal(Cereal.PEAS, 1f)
        assertEquals(
            cerealStorageImpl.getSpace(Cereal.PEAS),
            9f,
            0.01f
        )
    }

    @Test
    fun `getSpace should return the size of the container if it does not exist and can be created`() {
        val cerealStorageImpl2 = CerealStorageImpl(10f, 20f)
        assertEquals(
            cerealStorageImpl2.getSpace(Cereal.PEAS),
            10f,
            0.01f
        )
    }

    @Test
    fun `getSpace should return zero if the container does not exist and cannot be created`() {
        val cerealStorageImpl3 = CerealStorageImpl(10f, 15f)
        cerealStorageImpl3.addCereal(Cereal.PEAS, 10f)
        assertEquals(
            cerealStorageImpl3.getSpace(Cereal.BULGUR),
            0f,
            0.01f
        )
    }

    @Test
    fun `toString should return correct representation`() {
        val cerealStorageImpl = CerealStorageImpl(10f, 20f)
        cerealStorageImpl.apply {
            addCereal(Cereal.PEAS, 1f)
            addCereal(Cereal.RICE, 1f)
        }
        assertEquals(
            "Объём одного контейнера = 10.0, Совокупный объём хранилища = 20.0, Содержимое хранилища = {PEAS=1.0, RICE=1.0}",
            cerealStorageImpl.toString()
        )
    }
}