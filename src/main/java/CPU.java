import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CPU {
	ArrayList<Process> procesai = new ArrayList<Process>();
	ArrayList<Resource> resursai = new ArrayList<Resource>();

	ArrayList<Process> pasiruose_procesai = new ArrayList<Process>();
	//ArrayList<Process> blokuoti_procesai = new ArrayList<Process>();
	ArrayList<Process> sustabdyti_procesai = new ArrayList<Process>();
	Process aktyvus_procesas = null;

	ArrayList<Resource> laisvi_resursai = new ArrayList<Resource>();
	ArrayList<Resource> uzimti_resursai = new ArrayList<Resource>();

	LinkedHashMap<Process, String> blokuoti_procesai = new LinkedHashMap<Process, String>();

	// planuotojas ir jo funkcijos
	void planutojas() {
		while (true) {
			Process a = getReadyProcess();
			for (Process p : pasiruose_procesai) {
				if (p.priority > a.priority)
					a = p;
			}
			for (Process p : pasiruose_procesai) {
				if (!a.equals(p))
					p.increasePriority();
			}
			a.decreasePriority();
			this.aktyvus_procesas = a;
			a.run();
		}
	}

	Process getReadyProcess() {
		Process a = null;
		if (pasiruose_procesai.isEmpty()) {
			skirstytojas();
		}
		if (pasiruose_procesai.isEmpty()) {
			a = getReadyProcess();
			return a;
		}
		a = pasiruose_procesai.get(0);
		return a;
	}

	// skirstytojas ir jo funkcijos
	void skirstytojas(){
		for (Process p : blokuoti_procesai.keySet()) {
			Resource a = null;
			for (Resource r : laisvi_resursai){
				if (blokuoti_procesai.get(p).equals(r.name)){
					a = r;
					break;
				}
			}
			if (a == null)
				continue;
			laisvi_resursai.remove(a);
			uzimti_resursai.add(a);
			p.resourcesInPossesion.add(a);
			blokuoti_procesai.remove(p);
			pasiruose_procesai.add(p);
		}
	}

	// procesu primityvai
	void kurtiProcesa(Process p) {
		pasiruose_procesai.add(p);
	}

	void naikintiProcesa(Process p) {
		pasiruose_procesai.remove(p);
		sustabdyti_procesai.remove(p);
		blokuoti_procesai.remove(p);
	}

	void stabdytiProcesa(Process p) {
		pasiruose_procesai.remove(p);
		blokuoti_procesai.remove(p);
		sustabdyti_procesai.add(p);
	}

	void aktyvuotiProcesa(Process p) {
		sustabdyti_procesai.remove(p);
		pasiruose_procesai.add(p);
	}

	// resursu primityvai
	void kurtiResursa(String name) {
		kurtiResursa(name, null);
	}
	void kurtiResursa(String name, Integer adresatoId) {
		Resource r = new Resource(this.aktyvus_procesas, name, adresatoId);
		uzimti_resursai.add(r);
		this.aktyvus_procesas.generatedResources.add(r);
	}
	

	void naikintiResursa(Resource r) {
		uzimti_resursai.remove(r);
		r.parent.generatedResources.remove(r);
	}

	void prasytiResurso(String name) {
		blokuoti_procesai.put(this.aktyvus_procesas, name);
	}

	void atalaisvintiResursa(Resource r) {
		uzimti_resursai.remove(r);
		laisvi_resursai.add(r);
	}
}
