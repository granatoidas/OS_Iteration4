
public class EntryPoint {
	static CPU cpu = new CPU();

	public static void main(String[] args) {
		Process startStop = new Process(0, 0, "StarStop") {
			void run() {
				switch (this.segmentToRunFrom) {
				case 0:
					cpu.kurtiProcesa(this.id, "ReadFromHDD");
					cpu.kurtiProcesa(this.id, "JCL");
					cpu.kurtiProcesa(this.id, "Loader");
					cpu.kurtiProcesa(this.id, "MainProc");
					cpu.kurtiProcesa(this.id, "Interrupt");
					cpu.kurtiProcesa(this.id, "PrintLine");

					cpu.kurtiResursa("Kanalu irenginys");
					cpu.kurtiResursa("Supervizoriaus atmintis");
					for (int i = 0; i < 15; i++)
						cpu.kurtiResursa("Vartotojo atminties blokas");
					cpu.kurtiResursa("Kietasis diskas");
					
					
				case 1:

				}
			}
		};
		cpu.pasiruose_procesai.add(startStop);

	}

}
