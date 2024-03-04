/*
 * Created by Christopher Becker on 14/04/2023, 11:35
 * Copyright (c) 2023. All rights reserved.
 * Last modified 14/04/2023, 11:35
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.chrisbecker386.maintainer.data.model.dummy

import de.chrisbecker386.maintainer.data.entity.Step

val devSteps = listOf(
    // clean water tank
    Step(
        id = 1,
        order = 1,
        title = "Open lid",
        description = null,
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 2,
        order = 2,
        title = "Remove water tank",
        description = "Carefully lift the tank out of the machine. So that no water is spilled unnecessarily",
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 3,
        order = 3,
        title = "Clean water tank",
        description = "Clean the water tank properly with a brush, warm water and detergent. Then rinse again with clean water and dry the tank.",
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 4,
        order = 4,
        title = "Reinstall the water tank",
        description = null,
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 5,
        order = 5,
        title = "Close lit",
        description = null,
        completedDate = null,
        taskId = 1
    ),
    // unclogging
    Step(
        id = 6,
        order = 1,
        title = "Open lit",
        description = null,
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 7,
        order = 2,
        title = "Empty Tank",
        description = "Carefully empty the tank",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 8,
        order = 3,
        title = "add descaling agent to the tank",
        description = "Proportional to the size of the water tank: Dissolve the descaler in lukewarm water and then pour it into the water tank.",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 9,
        order = 4,
        title = "uncoggling",
        description = "Place a sufficiently large container under the coffee outlet. Let the machine warm up and then run the entire tank through once. If the container is full, stop briefly and empty the container. If the tank is empty, stop the machine. ",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 10,
        order = 5,
        title = "Flush out residues",
        description = "Fill the water tank again with ordinary water and let the tank run completely empty again. Carry out this procedure 2 times",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 11,
        order = 6,
        title = "Refill tank",
        description = null,
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 12,
        order = 7,
        title = "Close lit",
        description = null,
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 13,
        order = 1,
        title = "Prepare",
        description = "Before you begin defrosting, ensure you have enough time set aside to complete the process. Depending on the amount of ice buildup, defrosting may take several hours.",
        taskId = 3
    ),
    Step(
        id = 14,
        order = 2,
        title = "Remove Food",
        description = "Take out all the food from the freezer and transfer it to a cooler or another freezer to keep it frozen during the defrosting process. Alternatively, you can place it in a cooler with ice packs to keep it cold.",
        taskId = 3
    ),
    Step(
        id = 15,
        order = 3,
        title = "Turn Off the Freezer",
        description = "Locate the temperature control dial or switch on your freezer and turn it off. This prevents the freezer from cooling while you're defrosting it.",
        taskId = 3
    ),
    Step(
        id = 16,
        order = 4,
        title = "Open the Freezer Door",
        description = "Leave the freezer door open to allow the ice to melt more quickly. You can place towels or old newspapers around the base of the freezer to catch water drips and prevent them from pooling on the floor.",
        taskId = 3
    ),
    Step(
        id = 17,
        order = 5,
        title = "Speed Up Defrosting (Optional)",
        description = "To expedite the defrosting process, you can place bowls of hot water or use a hairdryer on a low setting to gently melt the ice. Be careful not to use excessive heat, as it can damage the freezer.",
        taskId = 3
    ),
    Step(
        id = 18,
        order = 6,
        title = "Remove Ice Buildup",
        description = "As the ice begins to melt, use a plastic scraper or spatula to carefully chip away the ice buildup from the walls and shelves of the freezer. Avoid using sharp objects that could puncture the freezer walls.",
        taskId = 3
    ),
    Step(
        id = 19,
        order = 7,
        title = "Wipe Down",
        description = "Once the ice has melted completely, use a sponge or cloth dampened with warm, soapy water to wipe down the interior of the freezer. This helps remove any remaining ice residue and clean the surfaces.",
        taskId = 3
    ),
    Step(
        id = 20,
        order = 8,
        title = "Dry the Interior",
        description = "Use a clean towel to dry the interior of the freezer thoroughly. Ensure that all moisture is removed to prevent the formation of new ice buildup.",
        taskId = 3
    ),
    Step(
        id = 21,
        order = 9,
        title = "Turn On the Freezer",
        description = "Once the interior is completely dry, plug the freezer back in or turn the temperature control dial to the desired setting. Allow the freezer to cool down to the appropriate temperature before returning the food.",
        taskId = 3
    ),
    Step(
        id = 22,
        order = 10,
        title = "Return Food",
        description = "Once the freezer has reached the desired temperature, return the food items to their respective places in the freezer.",
        taskId = 3
    ),
    Step(
        id = 23,
        order = 11,
        title = "Clean Exterior",
        description = "Take the opportunity to wipe down the exterior of the freezer with a damp cloth to remove any dust or spills that may have accumulated during the defrosting process.",
        taskId = 3
    ),
    Step(
        id = 24,
        taskId = 4,
        order = 1,
        title = "Gather Materials",
        description = "Before you begin, gather the necessary materials such as a bucket, gloves, a screwdriver (if necessary), and a cleaning brush."
    ),
    Step(
        id = 25,
        taskId = 4,
        order = 2,
        title = "Locate the Trap",
        description = "Find the location of the trap under the sink or behind the appliance where it is installed. It is typically located along the drain pipe."
    ),
    Step(
        id = 26,
        taskId = 4,
        order = 3,
        title = "Place Bucket",
        description = "Position a bucket or container under the trap to catch any water or debris that may spill out during the cleaning process."
    ),
    Step(
        id = 27,
        taskId = 4,
        order = 4,
        title = "Loosen Connections",
        description = "Using a wrench or pliers, carefully loosen the connections on both sides of the trap. Be prepared for some water to drain out."
    ),
    Step(
        id = 28,
        taskId = 4,
        order = 5,
        title = "Remove the Trap",
        description = "Once the connections are loosened, carefully remove the trap from the drain pipe. Empty any remaining water and debris into the bucket."
    ),
    Step(
        id = 29,
        taskId = 4,
        order = 6,
        title = "Clean the Trap",
        description = "Thoroughly clean the trap using warm, soapy water and a cleaning brush. Remove any buildup of grease, hair, or food particles."
    ),
    Step(
        id = 30,
        taskId = 4,
        order = 7,
        title = "Inspect for Damage",
        description = "Inspect the trap for any signs of damage such as cracks or corrosion. Replace the trap if necessary to prevent leaks."
    ),
    Step(
        id = 31,
        taskId = 4,
        order = 8,
        title = "Reassemble the Trap",
        description = "Once the trap is clean and inspected, reassemble it by reconnecting the connections on both sides. Ensure that the connections are tight to prevent leaks."
    ),
    Step(
        id = 32,
        taskId = 4,
        order = 9,
        title = "Test for Leaks",
        description = "Turn on the water and check for any leaks around the connections. Tighten the connections further if needed."
    ),
    Step(
        id = 33,
        taskId = 4,
        order = 10,
        title = "Dispose of Debris",
        description = "Dispose of any debris collected in the bucket properly. Avoid pouring grease down the drain as it can cause clogs."
    ),
    Step(
        id = 34,
        taskId = 5,
        order = 1,
        title = "Turn Off the Dryer",
        description = "Before you begin, ensure that the dryer is turned off and unplugged to prevent any accidents."
    ),
    Step(
        id = 35,
        taskId = 5,
        order = 2,
        title = "Pull Out the Dryer",
        description = "Carefully move the dryer away from the wall to access the vent hose. You may need someone to assist you, as dryers can be heavy."
    ),
    Step(
        id = 36,
        taskId = 5,
        order = 3,
        title = "Disconnect the Vent Hose",
        description = "Loosen the clamp securing the vent hose to the dryer's exhaust port. Remove the hose carefully to avoid damaging it."
    ),
    Step(
        id = 37,
        taskId = 5,
        order = 4,
        title = "Inspect the Vent Hose",
        description = "Check the vent hose for any signs of damage, such as holes, tears, or excessive lint buildup. Replace the hose if it is damaged."
    ),
    Step(
        id = 38,
        taskId = 5,
        order = 5,
        title = "Clean the Vent Hose",
        description = "Using a vacuum cleaner or a vent cleaning brush, remove any lint or debris trapped inside the vent hose. Ensure that it is completely clean."
    ),
    Step(
        id = 39,
        taskId = 5,
        order = 6,
        title = "Check the Exhaust Port",
        description = "Inspect the dryer's exhaust port for any obstructions or lint buildup. Clean it thoroughly using a vacuum cleaner or brush."
    ),
    Step(
        id = 40,
        taskId = 5,
        order = 7,
        title = "Reconnect the Vent Hose",
        description = "Once the vent hose and exhaust port are clean, reconnect the hose to the dryer's exhaust port and tighten the clamp securely."
    ),
    Step(
        id = 41,
        taskId = 5,
        order = 8,
        title = "Move the Dryer Back",
        description = "Carefully move the dryer back into its original position against the wall. Ensure that there is no kinking or crushing of the vent hose."
    ),
    Step(
        id = 42,
        taskId = 5,
        order = 9,
        title = "Plug In and Test",
        description = "Plug in the dryer and turn it on to test its operation. Ensure that there are no unusual noises or issues with airflow."
    ),
    Step(
        id = 43,
        taskId = 6,
        order = 1,
        title = "Turn Off the Dishwasher",
        description = "Ensure that the dishwasher is turned off and unplugged to prevent any accidents during the cleaning process."
    ),
    Step(
        id = 44,
        taskId = 6,
        order = 2,
        title = "Locate the Filter",
        description = "Find the location of the dishwasher filter. It is typically located at the bottom of the dishwasher, beneath the lower spray arm."
    ),
    Step(
        id = 45,
        taskId = 6,
        order = 3,
        title = "Remove the Lower Rack",
        description = "Pull out the lower rack of the dishwasher to access the filter assembly. Set the rack aside carefully."
    ),
    Step(
        id = 46,
        taskId = 6,
        order = 4,
        title = "Unlock and Remove the Filter",
        description = "Unlock the filter assembly by twisting or pressing the locking mechanism. Remove the filter carefully from its housing."
    ),
    Step(
        id = 47,
        taskId = 6,
        order = 5,
        title = "Inspect the Filter",
        description = "Inspect the filter for any food debris, grease, or other particles trapped in the mesh. Rinse the filter under running water to remove any buildup."
    ),
    Step(
        id = 48,
        taskId = 6,
        order = 6,
        title = "Clean the Filter",
        description = "Using a soft brush or sponge, gently scrub the filter to remove stubborn residue. Ensure that all surfaces are clean and free of debris."
    ),
    Step(
        id = 49,
        taskId = 6,
        order = 7,
        title = "Check for Damage",
        description = "Inspect the filter for any signs of damage such as cracks or holes. Replace the filter if it is damaged to ensure optimal dishwasher performance."
    ),
    Step(
        id = 50,
        taskId = 6,
        order = 8,
        title = "Reassemble the Filter",
        description = "Once the filter is clean and inspected, reassemble it by placing it back into its housing and locking it securely in place."
    ),
    Step(
        id = 51,
        taskId = 6,
        order = 9,
        title = "Return the Lower Rack",
        description = "Slide the lower rack back into the dishwasher, ensuring that it is properly aligned with the spray arm and other components."
    ),
    Step(
        id = 52,
        taskId = 6,
        order = 10,
        title = "Plug In and Test",
        description = "Plug in the dishwasher and turn it on to test its operation. Ensure that there are no unusual noises or issues with water drainage."
    ),
    Step(
        id = 53,
        taskId = 7,
        order = 1,
        title = "Turn Off the Dishwasher",
        description = "Ensure that the dishwasher is turned off and cool to the touch before adding special salt."
    ),
    Step(
        id = 54,
        taskId = 7,
        order = 2,
        title = "Locate the Salt Reservoir",
        description = "Find the salt reservoir compartment in the base of the dishwasher. It is usually located near the bottom of the unit."
    ),
    Step(
        id = 55,
        taskId = 7,
        order = 3,
        title = "Open the Reservoir Cap",
        description = "Twist or pull open the cap covering the salt reservoir. This may require a slight amount of force."
    ),
    Step(
        id = 56,
        taskId = 7,
        order = 4,
        title = "Fill the Reservoir",
        description = "Using a funnel, carefully pour the special dishwasher salt into the reservoir until it is full. Be sure not to spill salt into the dishwasher interior."
    ),
    Step(
        id = 57,
        taskId = 7,
        order = 5,
        title = "Check for Overflow",
        description = "Inspect the salt reservoir for any spilled salt around the opening. Clean up any spills to prevent them from entering the dishwasher."
    ),
    Step(
        id = 58,
        taskId = 7,
        order = 6,
        title = "Close the Reservoir Cap",
        description = "Once the reservoir is filled, securely close the cap to prevent salt from leaking out during dishwasher operation."
    ),
    Step(
        id = 59,
        taskId = 7,
        order = 7,
        title = "Run a Rinse Cycle",
        description = "After adding salt, run a rinse cycle to flush out any excess salt residue from the reservoir and ensure proper functioning of the dishwasher."
    ),
    Step(
        id = 60,
        taskId = 7,
        order = 8,
        title = "Check Salt Levels Regularly",
        description = "Monitor the salt levels in the reservoir regularly and refill as needed to maintain optimal dishwasher performance."
    ),
    Step(
        id = 61,
        taskId = 7,
        order = 9,
        title = "Dispose of Packaging",
        description = "Dispose of any empty salt packaging properly. Keep the salt stored in a dry place away from moisture."
    ),
    Step(
        id = 62,
        taskId = 8,
        order = 1,
        title = "Turn Off the Dishwasher",
        description = "Ensure that the dishwasher is turned off and cool to the touch before clearing the rinse aid dispenser."
    ),
    Step(
        id = 63,
        taskId = 8,
        order = 2,
        title = "Locate the Rinse Aid Dispenser",
        description = "Find the rinse aid dispenser compartment inside the dishwasher. It is usually located near the dishwasher door."
    ),
    Step(
        id = 64,
        taskId = 8,
        order = 3,
        title = "Open the Dispenser Lid",
        description = "Flip open or twist the lid of the rinse aid dispenser to access the reservoir inside."
    ),
    Step(
        id = 65,
        taskId = 8,
        order = 4,
        title = "Check Rinse Aid Level",
        description = "Inspect the level of rinse aid in the dispenser. If the level is low, refill the dispenser with a rinse aid solution."
    ),
    Step(
        id = 66,
        taskId = 8,
        order = 5,
        title = "Clean the Dispenser",
        description = "Using a soft cloth or sponge, wipe away any residue or buildup from the inside of the rinse aid dispenser. Ensure that the dispenser is clean and free from obstructions."
    ),
    Step(
        id = 67,
        taskId = 8,
        order = 6,
        title = "Replace the Lid",
        description = "Once the dispenser is clean, close the lid securely to prevent any spills or leaks during dishwasher operation."
    ),
    Step(
        id = 68,
        taskId = 8,
        order = 7,
        title = "Run a Rinse Cycle",
        description = "After clearing the rinse aid dispenser, run a rinse cycle to ensure that any remaining residue is flushed out of the system."
    ),
    Step(
        id = 69,
        taskId = 8,
        order = 8,
        title = "Monitor Rinse Aid Usage",
        description = "Monitor the rinse aid usage during dishwasher cycles. Refill the dispenser as needed to maintain optimal drying performance."
    ),
    Step(
        id = 70,
        taskId = 8,
        order = 9,
        title = "Dispose of Packaging",
        description = "Dispose of any empty rinse aid packaging properly. Keep the rinse aid stored in a cool, dry place away from direct sunlight."
    ),
    Step(
        id = 71,
        taskId = 9,
        order = 1,
        title = "Turn Off the Vacuum Cleaner",
        description = "Ensure that the vacuum cleaner is turned off and unplugged from the power source to prevent any accidents."
    ),
    Step(
        id = 72,
        taskId = 9,
        order = 2,
        title = "Locate the Filter Compartment",
        description = "Find the filter compartment on the vacuum cleaner. The location may vary depending on the model of the vacuum cleaner."
    ),
    Step(
        id = 73,
        taskId = 9,
        order = 3,
        title = "Remove the Filter",
        description = "Carefully remove the filter from its compartment. Some filters may require twisting or unlocking to remove them."
    ),
    Step(
        id = 74,
        taskId = 9,
        order = 4,
        title = "Shake Off Loose Debris",
        description = "Take the filter outside or to a trash can and gently shake it to remove any loose dust or debris."
    ),
    Step(
        id = 75,
        taskId = 9,
        order = 5,
        title = "Brush Off Remaining Debris",
        description = "Using a soft-bristled brush or a vacuum cleaner brush attachment, gently brush off any remaining dust or debris from the filter surface."
    ),
    Step(
        id = 76,
        taskId = 9,
        order = 6,
        title = "Wash the Filter (If Applicable)",
        description = "If the filter is washable, rinse it under running water to remove stubborn dirt and debris. Use mild detergent if necessary, and allow the filter to air dry completely before reinstalling it."
    ),
    Step(
        id = 77,
        taskId = 9,
        order = 7,
        title = "Inspect the Filter",
        description = "Inspect the filter for any signs of damage such as tears, holes, or excessive wear. Replace the filter if it is damaged to ensure optimal vacuum performance."
    ),
    Step(
        id = 78,
        taskId = 9,
        order = 8,
        title = "Reinstall the Filter",
        description = "Once the filter is clean and dry, reinstall it into the vacuum cleaner compartment. Ensure that it is securely in place before using the vacuum cleaner."
    ),
    Step(
        id = 79,
        taskId = 9,
        order = 9,
        title = "Dispose of Debris",
        description = "Dispose of any collected debris from the filter properly. Avoid shaking it indoors to prevent dust from spreading."
    ),
    Step(
        id = 80,
        taskId = 10,
        order = 1,
        title = "Prepare External Storage",
        description = "Connect an external storage device such as a USB flash drive or an external hard drive to your laptop. Ensure that the storage device has sufficient capacity to accommodate your backup."
    ),
    Step(
        id = 81,
        taskId = 10,
        order = 2,
        title = "Open Backup Software",
        description = "Launch your preferred backup software or use the built-in backup utility available on your laptop's operating system."
    ),
    Step(
        id = 82,
        taskId = 10,
        order = 3,
        title = "Select Backup Source",
        description = "Choose the files and folders you want to include in the backup. You can select specific files, folders, or the entire contents of your laptop's storage."
    ),
    Step(
        id = 83,
        taskId = 10,
        order = 4,
        title = "Choose Backup Destination",
        description = "Specify the external storage device connected to your laptop as the backup destination. Double-check that you have selected the correct device to avoid overwriting existing data."
    ),
    Step(
        id = 84,
        taskId = 10,
        order = 5,
        title = "Start Backup Process",
        description = "Initiate the backup process by clicking the 'Backup' or 'Start' button in the backup software. The software will begin copying your selected files and folders to the external storage device."
    ),
    Step(
        id = 85,
        taskId = 10,
        order = 6,
        title = "Monitor Progress",
        description = "Monitor the backup progress to ensure that all files are being successfully copied to the external storage device. Depending on the amount of data, the backup process may take some time."
    ),
    Step(
        id = 86,
        taskId = 10,
        order = 7,
        title = "Verify Backup Completion",
        description = "Once the backup process is complete, verify that all files have been successfully copied to the external storage device. Check for any error messages or incomplete backups."
    ),
    Step(
        id = 87,
        taskId = 10,
        order = 8,
        title = "Safely Eject External Storage",
        description = "After confirming the backup completion, safely eject the external storage device from your laptop to prevent data corruption or loss. Follow the proper procedure to eject the device."
    ),
    Step(
        id = 88,
        taskId = 10,
        order = 9,
        title = "Store Backup Device Securely",
        description = "Store the external storage device in a safe and secure location, preferably away from the laptop to prevent both devices from being damaged or lost simultaneously."
    ),
    Step(
        id = 89,
        taskId = 10,
        order = 10,
        title = "Schedule Regular Backups",
        description = "To ensure the safety of your data, establish a regular backup schedule and adhere to it. Consider automating the backup process to minimize the risk of data loss."
    ),
    Step(
        id = 90,
        taskId = 11,
        order = 1,
        title = "Park on a Level Surface",
        description = "Park your car on a flat and level surface. This ensures accurate oil level readings and prevents the car from rolling."
    ),
    Step(
        id = 91,
        taskId = 11,
        order = 2,
        title = "Turn Off the Engine",
        description = "Turn off the engine and allow it to cool down for a few minutes. Checking the oil level when the engine is hot can lead to inaccurate readings."
    ),
    Step(
        id = 92,
        taskId = 11,
        order = 3,
        title = "Locate the Dipstick",
        description = "Open the car's hood and locate the engine oil dipstick. It is usually a brightly colored handle labeled 'Engine Oil' or 'Oil'."
    ),
    Step(
        id = 93,
        taskId = 11,
        order = 4,
        title = "Remove the Dipstick",
        description = "Pull the dipstick out of its tube and wipe it clean with a rag or paper towel. Ensure that there are no oil drips or debris on the dipstick."
    ),
    Step(
        id = 94,
        taskId = 11,
        order = 5,
        title = "Reinsert the Dipstick",
        description = "Fully insert the dipstick back into its tube until it is seated properly. Make sure it is secured to prevent any oil leaks."
    ),
    Step(
        id = 95,
        taskId = 11,
        order = 6,
        title = "Remove the Dipstick Again",
        description = "Pull the dipstick out of its tube once more and hold it horizontally. Check the oil level against the markings on the dipstick."
    ),
    Step(
        id = 96,
        taskId = 11,
        order = 7,
        title = "Check Oil Level",
        description = "The oil level should be between the 'Min' and 'Max' markings on the dipstick. If it is below the 'Min' mark, add more oil as needed."
    ),
    Step(
        id = 97,
        taskId = 11,
        order = 8,
        title = "Inspect Oil Condition",
        description = "Observe the color and consistency of the oil on the dipstick. Fresh oil is typically amber or brown in color and free of debris. If the oil appears dark, gritty, or foamy, it may indicate contamination or engine problems."
    ),
    Step(
        id = 98,
        taskId = 11,
        order = 9,
        title = "Reinstall the Dipstick",
        description = "Once you have checked the oil level and condition, wipe the dipstick clean again and reinsert it into its tube securely."
    ),
    Step(
        id = 99,
        taskId = 11,
        order = 10,
        title = "Close the Hood",
        description = "Close the car's hood securely once you have finished checking the oil level. Ensure that it is properly latched to prevent accidents while driving."
    ),
    Step(
        id = 100,
        taskId = 12,
        order = 1,
        title = "Park on a Level Surface",
        description = "Park your car on a flat and level surface to ensure accurate tire pressure readings."
    ),
    Step(
        id = 101,
        taskId = 12,
        order = 2,
        title = "Prepare Tire Gauge",
        description = "Retrieve a tire pressure gauge. Make sure it is in good condition and properly calibrated."
    ),
    Step(
        id = 102,
        taskId = 12,
        order = 3,
        title = "Locate Tire Valve Stem",
        description = "Identify the valve stem on each tire. It is a small metal or rubber stem located on the wheel rim."
    ),
    Step(
        id = 103,
        taskId = 12,
        order = 4,
        title = "Remove Valve Cap",
        description = "Unscrew the valve cap from the valve stem and set it aside in a safe place. Be careful not to lose it."
    ),
    Step(
        id = 104,
        taskId = 12,
        order = 5,
        title = "Attach Tire Gauge",
        description = "Press the tire gauge firmly onto the valve stem until you hear a hissing sound. This indicates that the gauge is properly seated."
    ),
    Step(
        id = 105,
        taskId = 12,
        order = 6,
        title = "Read Tire Pressure",
        description = "Read the tire pressure displayed on the gauge. The recommended pressure for your car's tires can usually be found in the owner's manual or on a sticker inside the driver's door jamb."
    ),
    Step(
        id = 106,
        taskId = 12,
        order = 7,
        title = "Compare to Recommended Pressure",
        description = "Compare the measured tire pressure to the recommended pressure. If the measured pressure is lower than the recommended pressure, add air to the tire until it reaches the correct pressure."
    ),
    Step(
        id = 107,
        taskId = 12,
        order = 8,
        title = "Release Excess Air (If Necessary)",
        description = "If the measured pressure is higher than the recommended pressure, use the gauge's pressure release valve to release excess air until the tire reaches the correct pressure."
    ),
    Step(
        id = 108,
        taskId = 12,
        order = 9,
        title = "Repeat for All Tires",
        description = "Repeat the above steps for each tire, including the spare tire if applicable. Remember to replace the valve caps after checking each tire."
    ),
    Step(
        id = 109,
        taskId = 12,
        order = 10,
        title = "Replace Valve Caps and Stow Gauge",
        description = "Once you have checked all tires, securely screw the valve caps back onto the valve stems. Return the tire gauge to its storage location in your car."
    ),
    Step(
        id = 110,
        taskId = 13,
        order = 1,
        title = "Park the Bicycle",
        description = "Find a level surface to park your bicycle, ensuring it is stable and won't tip over during the tire pressure check."
    ),
    Step(
        id = 111,
        taskId = 13,
        order = 2,
        title = "Locate the Valve Stem",
        description = "Identify the valve stem on each tire. It is usually located near the rim and can be either a Presta or Schrader valve."
    ),
    Step(
        id = 112,
        taskId = 13,
        order = 3,
        title = "Remove Valve Cap",
        description = "Unscrew the valve cap from the valve stem and set it aside in a safe place. Be careful not to lose it."
    ),
    Step(
        id = 113,
        taskId = 13,
        order = 4,
        title = "Prepare Tire Gauge",
        description = "Retrieve a tire pressure gauge suitable for use with bicycle tires. Make sure it is in good condition and properly calibrated."
    ),
    Step(
        id = 114,
        taskId = 13,
        order = 5,
        title = "Attach Tire Gauge",
        description = "Press the tire gauge firmly onto the valve stem until you hear a hissing sound. This indicates that the gauge is properly seated."
    ),
    Step(
        id = 115,
        taskId = 13,
        order = 6,
        title = "Read Tire Pressure",
        description = "Read the tire pressure displayed on the gauge. The recommended pressure for your bicycle tires is usually imprinted on the sidewall of the tire."
    ),
    Step(
        id = 116,
        taskId = 13,
        order = 7,
        title = "Compare to Recommended Pressure",
        description = "Compare the measured tire pressure to the recommended pressure. If the measured pressure is lower than the recommended pressure, add air to the tire until it reaches the correct pressure."
    ),
    Step(
        id = 117,
        taskId = 13,
        order = 8,
        title = "Release Excess Air (If Necessary)",
        description = "If the measured pressure is higher than the recommended pressure, use the gauge's pressure release valve to release excess air until the tire reaches the correct pressure."
    ),
    Step(
        id = 118,
        taskId = 13,
        order = 9,
        title = "Repeat for All Tires",
        description = "Repeat the above steps for each tire on your bicycle. Remember to replace the valve caps after checking each tire."
    ),
    Step(
        id = 119,
        taskId = 13,
        order = 10,
        title = "Replace Valve Caps and Stow Gauge",
        description = "Once you have checked all tires, securely screw the valve caps back onto the valve stems. Return the tire gauge to its storage location."
    ),
    Step(
        id = 120,
        taskId = 14,
        order = 1,
        title = "Prepare the Bicycle",
        description = "Place your bicycle on a stable surface and ensure it is secure. You may want to elevate the rear wheel to allow easier access to the brakes."
    ),
    Step(
        id = 121,
        taskId = 14,
        order = 2,
        title = "Check Brake Lever Action",
        description = "Squeeze each brake lever individually and observe the action. The brake lever should engage smoothly and offer resistance."
    ),
    Step(
        id = 122,
        taskId = 14,
        order = 3,
        title = "Inspect Brake Pads",
        description = "Visually inspect the brake pads for wear. They should have sufficient thickness and be evenly worn. If the pads appear worn or uneven, they may need to be replaced."
    ),
    Step(
        id = 123,
        taskId = 14,
        order = 4,
        title = "Check Brake Cable Tension",
        description = "Inspect the brake cables for any signs of fraying or damage. Ensure that the cables are properly tensioned and that there is no slack when the brake levers are engaged."
    ),
    Step(
        id = 124,
        taskId = 14,
        order = 5,
        title = "Adjust Brake Cable Tension",
        description = "If necessary, adjust the brake cable tension using the barrel adjuster located on the brake calipers or brake levers. Turn the adjuster clockwise to tighten the cable and counterclockwise to loosen it."
    ),
    Step(
        id = 125,
        taskId = 14,
        order = 6,
        title = "Test Braking Performance",
        description = "Take your bicycle for a short test ride and apply the brakes at various speeds. Ensure that both the front and rear brakes engage smoothly and bring the bicycle to a complete stop without any issues."
    ),
    Step(
        id = 126,
        taskId = 14,
        order = 7,
        title = "Verify Brake Alignment",
        description = "Check that the brake pads align properly with the wheel rims when engaged. They should make even contact with the rims and not rub against the tire sidewalls."
    ),
    Step(
        id = 127,
        taskId = 14,
        order = 8,
        title = "Clean Brake Surfaces",
        description = "Use a clean rag or paper towel to wipe down the brake pads and wheel rims. Remove any debris or buildup that may affect braking performance."
    ),
    Step(
        id = 128,
        taskId = 14,
        order = 9,
        title = "Perform Final Inspection",
        description = "After making any adjustments, perform a final inspection of the brakes to ensure they are functioning properly. Make any additional adjustments as needed."
    ),
    Step(
        id = 129,
        taskId = 14,
        order = 10,
        title = "Maintenance and Repair",
        description = "If you encounter any issues that cannot be resolved through adjustment, consider seeking professional maintenance or repair services for your bicycle brakes."
    )
)