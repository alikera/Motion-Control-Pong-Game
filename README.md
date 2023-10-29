# Motion-Controlled Pong Game
This project aims to replicate the classic Pong game with a unique twist – it is controlled using the accelerometer and gyroscope sensors on Android smartphones. The game environment includes a `ball` and a `paddle`, and it utilizes the Canvas library to render two-dimensional components on the screen. Physical formulas are applied to simulate the motion of the ball and paddle.

## Basic Mode
In this mode, the device is placed on a flat surface such as a table. The game area on the table spans 50 cm in length. The device can move 25 cm to the right and 25 cm to the left on the table. The paddle moves in the same direction as the device's movement. Additionally, rotating the device around its perpendicular axis causes the paddle to rotate correspondingly.

## Advanced Mode
In the advanced mode, the device is not constrained to a table; instead, it is held parallel to the ground by the user. Gravitational acceleration is utilized to move the paddle along the X-axis of the screen. When the ball collides with the paddle, applying a sudden upward acceleration in the Z direction enhances the ball's travel distance on the plane. This feature mimics the behavior of an actual ping pong paddle, where the force applied upon impact affects the ball's speed. The paddle's rotation in this mode mirrors that of the normal mode and is controlled by the gyroscope sensor.
