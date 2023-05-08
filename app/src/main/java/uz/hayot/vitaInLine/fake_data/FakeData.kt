package uz.hayot.vitaInLine.fake_data

import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.models.ChildRcModel
import uz.hayot.vitaInLine.models.ParentRcModel
import uz.hayot.vitaInLine.models.Pill

class FakeData {
    companion object {
        fun getParentRcData(): MutableList<ParentRcModel> {
            val listParent: MutableList<ParentRcModel> = ArrayList()
            var listChild: MutableList<ChildRcModel> = ArrayList()
            listChild = getChildRcData()
            listParent.clear()
            listParent.add(ParentRcModel("Parasetamol 500 mg", listChild))
            listParent.add(ParentRcModel("Parasetamol 500 mg", listChild))
            listParent.add(ParentRcModel("Parasetamol 500 mg", listChild))
            listParent.add(ParentRcModel("Parasetamol 500 mg", listChild))
            listParent.add(ParentRcModel("Parasetamol 500 mg", listChild))
            return listParent
        }


        private fun getChildRcData(): MutableList<ChildRcModel> {
            val listChild: MutableList<ChildRcModel> = ArrayList()
            listChild.clear()
            listChild.add(ChildRcModel(false, "8:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "12:00", "2 ta tabletka", "Och qoringa"))
            listChild.add(ChildRcModel(false, "18:00", "2 ta tabletka", "Och qoringa"))
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
    }
}