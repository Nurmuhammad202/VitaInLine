package uz.hayot.vitaInLine.fake_data

import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.models.ChildRcModel
import uz.hayot.vitaInLine.models.DateModel
import uz.hayot.vitaInLine.models.ParentRcModel
import uz.hayot.vitaInLine.models.Pill

class FakeData {
    companion object {
        fun getParentRcData(): MutableList<ParentRcModel> {
            val listParent: MutableList<ParentRcModel> = ArrayList()

            val listChild1: MutableList<ChildRcModel> = getChild1RcData()
            val listChild2: MutableList<ChildRcModel> = getChild2RcData()
            val listChild3: MutableList<ChildRcModel> = getChild3RcData()
            val listChild4: MutableList<ChildRcModel> = getChild4RcData()


            listParent.clear()
            listParent.add(ParentRcModel(0, "Parasetamol 500 mg", listChild1))
            listParent.add(ParentRcModel(0, "Parasetamol 500 mg", listChild2))
            listParent.add(ParentRcModel(0, "Parasetamol 500 mg", listChild3))
            listParent.add(ParentRcModel(0, "Parasetamol 500 mg", listChild4))
            listParent.add(ParentRcModel(0, "Parasetamol 500 mg", listChild1))
            return listParent
        }


        private fun getChild1RcData(): MutableList<ChildRcModel> {
            val listChild: MutableList<ChildRcModel> = ArrayList()
            listChild.clear()
            listChild.add(ChildRcModel(false, "8:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "18:00", "2 ta tabletka", "Och qoringa"))
            return listChild
        }

        private fun getChild2RcData(): MutableList<ChildRcModel> {
            val listChild: MutableList<ChildRcModel> = ArrayList()
            listChild.clear()
            listChild.add(ChildRcModel(false, "8:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            return listChild
        }

        private fun getChild3RcData(): MutableList<ChildRcModel> {
            val listChild: MutableList<ChildRcModel> = ArrayList()
            listChild.clear()
            listChild.add(ChildRcModel(false, "8:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "18:00", "2 ta tabletka", "Och qoringa"))
            return listChild
        }

        private fun getChild4RcData(): MutableList<ChildRcModel> {
            val listChild: MutableList<ChildRcModel> = ArrayList()
            listChild.clear()
            listChild.add(ChildRcModel(false, "8:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "18:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            return listChild
        }


        fun getPillData(): MutableList<Pill> {
            val list: MutableList<Pill> = ArrayList()
            list.clear()
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol 500 ",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol 500 mg",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol 600 ",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol 700",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            list.add(
                Pill(
                    R.drawable.simple_pill_photo,
                    "Parasetamol",
                    "Анальгетик-антипиретик. Обладает жаропонижающим и болеутоляющим действием. Блокирует ЦОГ-1 и ЦОГ-2 преимущественно в ЦНС, воздействуя на центры боли и терморегуляции. В воспаленных тканях клеточные пероксидазы нейтрализуют влияние парацетамола на ЦОГ, что объясняет практически полное отсутствие противовоспалительного эффекта. Поскольку парацетамол обладает чрезвычайно малым влиянием па синтез простагландинов в периферических тканях, он не изменяет водно-электролитный обмен и не вызывает повреждения слизистой оболочки ЖКТ."
                )
            )
            return list
        }


        fun getHistoryFakeDate():MutableList<Any>{
            val result: MutableList<Any> = ArrayList()
            val parentData: MutableList<ParentRcModel> = ArrayList()
            val dateData: MutableList<DateModel> = ArrayList()

            val listChild1: MutableList<ChildRcModel> = getChild1RcData()
            val listChild2: MutableList<ChildRcModel> = getChild2RcData()
            val listChild3: MutableList<ChildRcModel> = getChild3RcData()
            val listChild4: MutableList<ChildRcModel> = getChild4RcData()


            result.add(DateModel("03.02.2023"))
            result.add(ParentRcModel(0, "Parasetamol 500 mg", listChild1))
            result.add(ParentRcModel(0, "Parasetamol 100 mg", listChild2))
            dateData.add(DateModel("04.02.2023"))
            result.add(ParentRcModel(0, "Parasetamol 500 mg", listChild3))
            result.add(ParentRcModel(0, "Parasetamol 500 mg", listChild4))
            result.add(ParentRcModel(0, "Parasetamol 600 mg", listChild2))
            dateData.add(DateModel("14.12.2022"))
            result.add(ParentRcModel(0, "Parasetamol 500 mg", listChild4))
            result.add(ParentRcModel(0, "Parasetamol 700 mg", listChild2))
            result.add(ParentRcModel(0, "Parasetamol 500 mg", listChild1))
            result.add(ParentRcModel(0, "Parasetamol 800 mg", listChild2))

          return result
        }
    }
}