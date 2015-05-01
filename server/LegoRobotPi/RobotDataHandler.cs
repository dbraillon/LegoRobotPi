using Raspberry.IO.GeneralPurpose;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LegoRobotPi
{
    public class RobotDataHandler : IDataHandler
    {
        private OutputPinConfiguration TurnLeftPin;
        private OutputPinConfiguration TurnRightPin;
        private OutputPinConfiguration GoForwardPin;
        private OutputPinConfiguration GoBackwardPin;
        private GpioConnection GpioConnection;

        public RobotDataHandler()
        {
            TurnLeftPin = ConnectorPin.P1Pin12.Output().Revert().Disable();
            TurnRightPin = ConnectorPin.P1Pin16.Output().Revert().Disable();
            GoForwardPin = ConnectorPin.P1Pin18.Output().Revert().Disable();
            GoBackwardPin = ConnectorPin.P1Pin22.Output().Revert().Disable();
            GpioConnection = new GpioConnection(TurnLeftPin, TurnRightPin, GoForwardPin, GoBackwardPin);
        }

        public void HandleIncomingData(string data)
        {
            switch (data)
            {
                case "left":
                {
                    Console.WriteLine("I turn left");
                    GpioConnection.Pins[TurnRightPin].Enabled = false;
                    GpioConnection.Pins[TurnLeftPin].Enabled = true;
                    break;
                }
                case "right":
                {
                    Console.WriteLine("I turn right");
                    GpioConnection.Pins[TurnLeftPin].Enabled = false;
                    GpioConnection.Pins[TurnRightPin].Enabled = true;
                    break;
                }
                case "forward":
                {
                    Console.WriteLine("I go forward");
                    GpioConnection.Pins[GoBackwardPin].Enabled = false;
                    GpioConnection.Pins[GoForwardPin].Enabled = true;
                    break;
                }
                case "backward":
                {
                    Console.WriteLine("I go backward");
                    GpioConnection.Pins[GoForwardPin].Enabled = false;
                    GpioConnection.Pins[GoBackwardPin].Enabled = true;
                    break;
                }
                case "stop_turn":
                {
                    Console.WriteLine("Stop turn");
                    GpioConnection.Pins[TurnLeftPin].Enabled = false;
                    GpioConnection.Pins[TurnRightPin].Enabled = false;
                    break;
                }
                case "stop_go":
                {
                    Console.WriteLine("Stop go");
                    GpioConnection.Pins[GoForwardPin].Enabled = false;
                    GpioConnection.Pins[GoBackwardPin].Enabled = false;
                    break;
                }
                default:
                {
                    Console.WriteLine(data);
                    break;
                }
            }
        }

        public void Dispose()
        {
            GpioConnection.Pins[TurnLeftPin].Enabled = false;
            GpioConnection.Pins[TurnRightPin].Enabled = false;
            GpioConnection.Pins[GoForwardPin].Enabled = false;
            GpioConnection.Pins[GoBackwardPin].Enabled = false;
            GpioConnection.Close();
        }
    }
}
