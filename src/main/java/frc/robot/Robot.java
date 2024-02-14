package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import edu.wpi.first.math.util.Units;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private SwerveDrive swerveDrive;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
public void teleopInit() {
    try {
        File swerveFile = new File(Filesystem.getDeployDirectory(), "swervedrive.json");
        if (!swerveFile.exists()) {
            System.err.println("Swerve file not found: " + swerveFile.getAbsolutePath());
            return;
        }

        SwerveDrive swerveDrive = new SwerveParser(swerveFile).createSwerveDrive(Units.feetToMeters(14.5));
        // Use swerveDrive object for further operations
    } catch (IOException e) {
        System.err.println("Error initializing SwerveDrive:");
        e.printStackTrace();
        // Handle the exception appropriately, such as logging the error or taking corrective action
    }

}


  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Update SmartDashboard with encoder offset readings
    if (swerveDrive != null) {
      for (int i = 0; i < swerveDrive.getModules().length; i++) {
        SmartDashboard.putNumber("Encoder Offset " + i, swerveDrive.getModules()[i].getAbsolutePosition());
      }
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
